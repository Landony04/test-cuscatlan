package com.landony.data.di

import com.landony.domain.repositories.PostsRepository
import com.landony.domain.useCases.PostsUseCase
import com.landony.domain.useCases.PostsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    /**
     * GET ALL POSTS
     * @provides of POSTS
     * @param postsRepository
     */
    @Provides
    @ViewModelScoped
    fun providePosts(
        postsRepository: PostsRepository
    ): PostsUseCase = PostsUseCaseImpl(postsRepository)
}