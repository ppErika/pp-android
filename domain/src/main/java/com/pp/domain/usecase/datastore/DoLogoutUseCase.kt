package com.pp.domain.usecase.datastore

import com.pp.domain.repository.DataStoreRepository
import javax.inject.Inject

class DoLogoutUseCase @Inject constructor(
    private val repository: DataStoreRepository
){
    suspend operator fun invoke() = repository.doLogout()
}