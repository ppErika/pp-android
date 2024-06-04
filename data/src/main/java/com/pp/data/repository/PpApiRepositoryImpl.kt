package com.pp.data.repository

import androidx.annotation.WorkerThread
import com.pp.data.dao.DiaryDao
import com.pp.data.datasource.server.PpApiDataSource
import com.pp.data.entity.Diary
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PpApiRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao,
    private val ppApiDataSource: PpApiDataSource
) : PpApiRepository {
    val allDiaries: List<Diary> = diaryDao.getAll()

    override suspend fun oauthToken(
        remoteError: RemoteError,
        oauthTokenRequest: OauthTokenRequest
    ): OauthTokenResponse? {
        return ppApiDataSource.oauthToken(remoteError, oauthTokenRequest)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(diary: Diary) {
        diaryDao.insert(diary)
    }
}