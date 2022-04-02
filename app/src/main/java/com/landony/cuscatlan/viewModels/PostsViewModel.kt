package com.landony.cuscatlan.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landony.domain.common.Result
import com.landony.domain.entities.PostsUI
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
    private val postsUseCase: PostsUseCase
) : ViewModel() {

    private val _postsInformation = MutableLiveData<Result<ArrayList<PostsUI>>>()
    val postsInformation: LiveData<Result<ArrayList<PostsUI>>> get() = _postsInformation

    fun getAllPosts() {
        viewModelScope.launch {
            postsUseCase.invokePosts()
                .collect {
                    _postsInformation.value = it
                }
        }
    }
}