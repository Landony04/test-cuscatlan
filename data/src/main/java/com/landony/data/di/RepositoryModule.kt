package com.landony.data.di

import com.landony.data.api.CuscatlanApiService
import com.landony.data.repositories.PostsRepositoryImpl
import com.landony.data.util.DispatcherProvider
import com.landony.domain.repositories.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Repositories for Posts -------------------->
     */
    @Provides
    fun providePostsRepository(
        @CuscatlanServices
        cuscatlanApiService: CuscatlanApiService,
        dispatcherProvider: DispatcherProvider
    ): PostsRepository {
        return PostsRepositoryImpl(
            cuscatlanApiService,
            dispatcherProvider
        )
    }
}