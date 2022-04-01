@file:Suppress("ObjectLiteralToLambda")

package com.landony.data.di

import com.landony.data.api.CuscatlanApiService
import com.landony.data.util.DefaultDispatcherProvider
import com.landony.data.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIME_OUT_SECONDS = 1000L

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://jsonplaceholder.typicode.com/"

    @CuscatlanServices
    @Provides
    fun provideApiMethods(
        @BaseUrl baseUrl: String,
        @OkhttpByKey okHttpClient: OkHttpClient
    ): CuscatlanApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .callFactory { request ->
                Timber.d("url: ${request.url}")
                okHttpClient.newCall(request)
            }
            .build()
            .create(CuscatlanApiService::class.java)

        return retrofit
    }

    @OkhttpByKey
    @Provides
    fun providesOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient().newBuilder()

        httpClient.apply {
            readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
        }

        httpClient.addNetworkInterceptor(loggingInterceptor)

        return httpClient.build()
    }

    @Provides
    fun provideLogInterceptor() = HttpLoggingInterceptor()

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}