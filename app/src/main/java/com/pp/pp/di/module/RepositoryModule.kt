package com.pp.pp.di.module

import com.pp.data.datasource.server.PpApiDataSourceImpl
import com.pp.data.repository.PpApiRepositoryImpl
import com.pp.domain.repository.PpApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePpApiRepository(ppApiDataSourceImpl: PpApiDataSourceImpl): PpApiRepository {
        return PpApiRepositoryImpl(ppApiDataSourceImpl)
    }

}