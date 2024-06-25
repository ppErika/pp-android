package com.pp.community.di.module

import com.pp.data.datasource.server.PpApiDataSourceImpl
import com.pp.data.repository.PpApiRepositoryImpl
import com.pp.data.repository.RoomRepositoryImpl
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.repository.RoomRepository
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

    @Provides
    @Singleton
    fun provideRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository {
        return roomRepositoryImpl
    }

}