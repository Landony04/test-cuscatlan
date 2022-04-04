package com.landony.cuscatlan.viewModels

import androidx.lifecycle.*
import com.landony.domain.common.Result
import com.landony.domain.entities.CommentsByPostResultUI
import com.landony.domain.entities.PhotosByPostUI
import com.landony.domain.entities.PostsUI
import com.landony.domain.useCases.CommentsByPostUseCase
import com.landony.domain.useCases.PhotosByPostUseCase
import com.landony.domain.useCases.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This is viewModel of posts
 *
 * @param postsUseCase interface.
 * @constructor Creates an PostsViewModel.
 */
@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase,
    private val commentsByPostUseCase: CommentsByPostUseCase,
    private val photosByPostUseCase: PhotosByPostUseCase
) : ViewModel() {

    private val _postsInformation = MutableLiveData<Result<ArrayList<PostsUI>>>()
    val postsInformation: LiveData<Result<ArrayList<PostsUI>>> get() = _postsInformation

    private val _commentsByPostParameters = MutableLiveData<String>()
    private val _commentsByPostInformation = _commentsByPostParameters.switchMap {
        commentsByPostUseCase.invokeCommentsByPost(it).asLiveData(viewModelScope.coroutineContext)
    }
    val commentsInformation: LiveData<Result<ArrayList<CommentsByPostResultUI>>> get() = _commentsByPostInformation

    private val _photosByPostParameters = MutableLiveData<String>()
    private val _photosByPostInformation = _photosByPostParameters.switchMap {
        photosByPostUseCase.invokePhotosByPost(it).asLiveData(viewModelScope.coroutineContext)
    }
    val photosInformation: LiveData<Result<ArrayList<PhotosByPostUI>>> get() = _photosByPostInformation

    fun getAllPosts() {
        viewModelScope.launch {
            postsUseCase.invokePosts()
                .collect {
                    _postsInformation.value = it
                }
        }
    }

    fun getCommentsByPost(idPost: String) {
        _commentsByPostParameters.value = idPost
    }

    fun getPhotosByPost(idPost: String) {
        _photosByPostParameters.value = idPost
    }
}