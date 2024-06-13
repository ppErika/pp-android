package com.pp.pp.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pp.data.remote.api.PpApi
import com.pp.data.remote.api.PpAuthenticationApi
import com.pp.data.remote.api.PpRefreshApi
import com.pp.pp.widget.AccessTokenInterceptor
import com.pp.pp.widget.AuthAuthenticator
import com.pp.pp.widget.NullOnEmptyConverterFactory
import com.pp.pp.widget.RefreshTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.setLenient().create()
    }

    @[Provides Singleton AuthenticatedClient]
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(loggingInterceptor())
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }
    @[Provides Singleton PublicClient]
    fun provideUnAccessOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }
    @[Provides Singleton TokenRefreshClient]
    fun provideRefreshOkHttpClient(
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(refreshTokenInterceptor)
            .addInterceptor(loggingInterceptor())
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("authenticationRetrofit")
    fun provideAuthenticationRetrofit(
        @AuthenticatedClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return createRetrofit(okHttpClient, gsonConverterFactory)

    }
    @Provides
    @Singleton
    @Named("retrofit")
    fun provideRetrofit(
        @PublicClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return createRetrofit(okHttpClient, gsonConverterFactory)

    }
    @Provides
    @Singleton
    @Named("refreshTokenRetrofit")
    fun provideRefreshTokenRetrofit(
        @TokenRefreshClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return createRetrofit(okHttpClient, gsonConverterFactory)
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(com.pp.pp.widget.NetworkUtils.BASE_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providePpAuthenticationApiService(@Named("authenticationRetrofit") retrofit: Retrofit): PpAuthenticationApi {
        return retrofit.create(PpAuthenticationApi::class.java)
    }
    @Provides
    @Singleton
    fun provideApiService(@Named("retrofit") retrofit: Retrofit): PpApi {
        return retrofit.create(PpApi::class.java)
    }
    @Provides
    @Singleton
    fun provideRefreshTokenApiService(@Named("refreshTokenRetrofit") retrofit: Retrofit): PpRefreshApi {
        return retrofit.create(PpRefreshApi::class.java)
    }
}