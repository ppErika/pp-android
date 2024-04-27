package com.pp.data.base

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pp.domain.model.errormodel.CommonErrorResponseInfo
import com.pp.domain.model.errormodel.ErrorResponseInfo
import com.pp.domain.utils.ErrorType
import com.pp.domain.utils.RemoteError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository {
    companion object {
        private const val TAG = "BaseRemoteRepository"
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    suspend inline fun <T> safeApiCall(emitter: RemoteError, call: suspend () -> Response<T>): T? {
        return try {
            val response = call.invoke()
            Log.d("EJ_LOG", "safeApiCall : $response")
            when(response.isSuccessful){
                true->{ response.body()}
                false -> {
                    val errorBody = getApiErrorMessage(response.errorBody())
                    Log.d("EJ_LOG", "errorBody : ${errorBody}")
                    errorBody?.let {
                        emitter.onApiError(it)
                    }?: kotlin.run {
                        val errorBody = getApiCommonErrorMessage(response.errorBody())
                        errorBody?.let {
                            emitter.onApiCommonError(it)
                        }
                    }
                    null
                }
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                e.printStackTrace()
                Log.e("EJ_LOG", "API Call error: ${e.localizedMessage}", e.cause)
                when(e){
                    is HttpException -> {
                        if(e.code() == 401) emitter.onError(ErrorType.SESSION_EXPIRED)
                        else {
                            val body = e.response()?.errorBody()
                            emitter.onError(getErrorMessage(body))
                        }
                    }
                    is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                    is IOException -> emitter.onError(ErrorType.NETWORK)
                    else -> emitter.onError(ErrorType.UNKNOWN)
                }
            }
            null
        }
    }


    inline fun <T> safeApiCallNoContext(emitter: RemoteError, callFunction: () -> T): T? {
        return try{
            val myObject = callFunction.invoke()
            myObject
        }catch (e: Exception){
            e.printStackTrace()
            Log.e("BaseRemoteRepo", "Call error: ${e.localizedMessage}", e.cause)
            when(e){
                is HttpException -> {
                    if(e.code() == 401) emitter.onError(ErrorType.SESSION_EXPIRED)
                    else {
                        val body = e.response()?.errorBody()
                        emitter.onError(getErrorMessage(body))
                    }
                }
                is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                is IOException -> emitter.onError(ErrorType.NETWORK)
                else -> emitter.onError(ErrorType.UNKNOWN)
            }
            null
        }
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }

    fun getApiErrorMessage(errorInfo: ResponseBody?): ErrorResponseInfo? {
        return try {
            errorInfo?.let {
                val gson = Gson()
                val type = object : TypeToken<ErrorResponseInfo>() {}.type
                return gson.fromJson(it.charStream(), type)
            }
        } catch (e: Exception) {
            return null
        }
    }

    fun getApiCommonErrorMessage(errorInfo: ResponseBody?): CommonErrorResponseInfo? {
        return try {
            errorInfo?.let {
                val gson = Gson()
                val type = object : TypeToken<CommonErrorResponseInfo>() {}.type
                return gson.fromJson(it.charStream(), type)
            }
        } catch (e: Exception) {
            return null
        }
    }
}