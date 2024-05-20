package com.pp.domain.usecase.datastore

import com.pp.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetAccessTokenUseCase @Inject constructor(
    private val repository: DataStoreRepository
){
    suspend operator fun invoke(accessToken: String) = repository.setAccessToken(accessToken)
}