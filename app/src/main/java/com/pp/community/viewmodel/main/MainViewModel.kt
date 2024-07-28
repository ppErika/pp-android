package com.pp.community.viewmodel.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pp.community.base.BaseViewModel
import com.pp.community.utils.DecodeJwtUtil
import com.pp.community.widget.SingleFlowEvent
import com.pp.domain.model.post.GetPostsRequest
import com.pp.domain.model.post.PostModel
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.model.token.RevokeTokenRequest
import com.pp.domain.model.users.GetUserProfileResponse
import com.pp.domain.usecase.datastore.DoLogoutUseCase
import com.pp.domain.usecase.datastore.GetAccessTokenUseCase
import com.pp.domain.usecase.datastore.SetAccessTokenUseCase
import com.pp.domain.usecase.mydiary.GetAllMyDiaryUseCase
import com.pp.domain.usecase.post.GetPostsUseCase
import com.pp.domain.usecase.token.OauthTokenUseCase
import com.pp.domain.usecase.token.RevokeTokenUseCase
import com.pp.domain.usecase.users.DeleteUserUseCase
import com.pp.domain.usecase.users.GetUserProfileUseCase
import com.pp.domain.usecase.users.UserRegisteredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isUserRegisteredUseCase: UserRegisteredUseCase,
    private val oauthTokenUseCase: OauthTokenUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val revokeTokenUseCase: RevokeTokenUseCase,
    private val doLogoutUseCase: DoLogoutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val decodeJwtUtil: DecodeJwtUtil,
    private val getAllMyDiaryUseCase: GetAllMyDiaryUseCase,
) : BaseViewModel() {
    // 공통
    var appBarTitle = mutableStateOf("")
        private set
    private val _movePageEvent = SingleFlowEvent<String>()
    val movePageEvent = _movePageEvent.flow

    // 로그인
    var isLogin = mutableStateOf(false)
        private set
    var isSelectedTerms1 = mutableStateOf(false)
        private set
    var isSelectedTerms2 = mutableStateOf(false)
        private set
    var isSelectedTermsAll = mutableStateOf(false)
        private set

    // 커뮤니티
    var communityPostList = mutableStateListOf<PostModel>()
        private set

    private var lastId: Int? = null
    private var kakaoIdToken: String = ""
    var reportPostId: Int = -1

    // 나의 일기
    private val _postList = MutableLiveData<List<PostModel>>()
    val postList: LiveData<List<PostModel>> get() = _postList

    // 탈퇴
    private val _deleteResult = SingleFlowEvent<Boolean>()
    val deleteResult = _deleteResult.flow

    // 프로필
    var profileInfo = mutableStateOf<GetUserProfileResponse?>(null)
        private set

    /**
     * 로그인
     */
    fun setAppBarTitle(title: String) {
        appBarTitle.value = title
    }

    fun isUserRegistered(idToken: String){
        viewModelScope.launch {
            val response = isUserRegisteredUseCase.execute(this@MainViewModel,"kakao",idToken)
            response?.let{
                Log.d("EJ_LOG","isUserReigstered : $it")
                setKakaoIdToken(idToken)
                // true -> oauthToken 호출
                // false -> 회원가입 페이지로 이동
                when(it.data.isRegistered){
                    true -> getOauthToken()
                    false -> _movePageEvent.emit("termsOfUse")
                }
//                _movePageEvent.emit("termsOfUse")

            }
        }
    }

    fun getOauthToken() {
        val oauthTokenRequest = OauthTokenRequest().apply {
            client_id = "kauth.kakao.com"
            client_assertion = kakaoIdToken
            grant_type="client_credentials"
        }
        viewModelScope.launch {
            val response = oauthTokenUseCase.execute(this@MainViewModel, oauthTokenRequest)
            response?.let {
                setAccessToken(it)
            }
        }
    }
    private fun setAccessToken(oauthTokenResponse: OauthTokenResponse) {
        viewModelScope.launch {
            setAccessTokenUseCase.invoke(oauthTokenResponse)
        }
        getAccessToken()
    }

    fun getAccessToken(){
        viewModelScope.launch {
            getAccessTokenUseCase.invoke().collect{
                it?.let{
                    isLogin.value = true
                }?:{
                    isLogin.value = false
                }
                Log.d("EJ_LOG","getAccessToken : $it")
            }
        }
    }
    suspend fun getAccessToken2(): String? {
        return withContext(Dispatchers.IO) {
            val token = getAccessTokenUseCase.invoke().first()
            Log.d("EJ_LOG", "getAccessToken : $token")
            token
        }
    }
    fun setKakaoIdToken(idToken: String){
        kakaoIdToken = idToken
    }
    fun getKakaoIdToken(): String{
        return kakaoIdToken
    }

    /**
     * 나의 일기
     */
    fun fetchMyDiaryList() {
        viewModelScope.launch {
            val response = getAllMyDiaryUseCase.execute()
            val posts = response.map { diary ->
                val thumbnailUrl = diary.images?.firstOrNull()?.let { byteArrayToBase64(it) } ?: ""
                val dateFormat = SimpleDateFormat("yyyy-MM-dd") // 원하는 날짜 형식으로 지정
                val formattedDate = dateFormat.format(diary.createDate)

                PostModel(
                    type = "DIARY",
                    id = diary.id,
                    thumbnailUrl = thumbnailUrl,
                    title = diary.title,
                    createDate = formattedDate
                )
            }
            _postList.postValue(posts)
        }
    }

    fun byteArrayToBase64(byteArray: ByteArray): String {
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
    }

    /**
     * 커뮤니티
     */
    fun getPostList(isFirst: Boolean = true) {
        val getPostsRequest = if(isFirst){
            GetPostsRequest().apply {  }
        }else{
            lastId?.let{
                GetPostsRequest().apply {
                    this.lastId = it
                }
            }
        }
        getPostsRequest?.let{
            viewModelScope.launch {
                val response = getPostsUseCase.execute(this@MainViewModel, it)
                response?.posts?.let{
                    if(isFirst){
                        communityPostList.clear()

                    }
                    communityPostList.addAll(it.apply{
                        this.filter{it.id!=reportPostId}.map{post -> post.type = "COMMUNITY"}
                    })
                    if(it.size==20){
                        lastId = it.last().id
                    }else{
                        lastId = null
                    }
                }
            }
        }

    }
    /**
     * 로그아웃
     */
    fun logout(){
        viewModelScope.launch {
            val revokeTokenRequest = RevokeTokenRequest().apply {
                token = getAccessToken2()?:""
            }
            val response = revokeTokenUseCase.execute(this@MainViewModel,revokeTokenRequest)
            Log.d("EJ_LOG","revokeToken : $response")
            response?.let{
                doLogoutUseCase.invoke()
                isLogin.value = false
            }
        }
    }
    /**
     * 탈퇴하기
     */
    fun deleteUser(){
        viewModelScope.launch {
            val userId = decodeJwtUtil.getUserId(token = getAccessToken2()?:"")
            val response = deleteUserUseCase.execute(this@MainViewModel,userId)
            response?.let{
                // TODO TEST
                _deleteResult.emit(true)
                doLogoutUseCase.invoke()
                isLogin.value = false

            }
        }
    }
    /**
     * 유저 프로필 조회
     */
    fun getUserProfile(){
        viewModelScope.launch {
            val userId = decodeJwtUtil.getUserId(token = getAccessToken2()?:"").toInt()
            val response = getUserProfileUseCase.execute(this@MainViewModel,userId)
            response?.let{
                profileInfo.value = it
                Log.d("EJ_LOG","getUserProfile : $it")
            }
        }
    }
}