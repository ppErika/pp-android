package com.pp.pp.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.pp.data.repository.DataStoreRepositoryImpl
import com.pp.domain.repository.DataStoreRepository
import com.pp.pp.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
        // just my preference of naming including the package name
        name = BuildConfig.APPLICATION_ID + "_preferences"
    )

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class UserPreferencesModule {

        // binds instance of MyUserPreferencesRepository
        @Binds
        @Singleton
        abstract fun bindUserPreferencesRepository(
            datastoreRepositoryImpl: DataStoreRepositoryImpl
        ): DataStoreRepository

        companion object {

            // provides instance of DataStore
            @Provides
            @Singleton
            fun provideUserDataStorePreferences(
                @ApplicationContext applicationContext: Context
            ): DataStore<Preferences> {
                return applicationContext.userDataStore
            }
        }
    }
}
