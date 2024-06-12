package com.pp.domain.usecase.datastore

import com.pp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRefreshTokenUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(): Flow<String?> = repository.getRefreshToken()
}