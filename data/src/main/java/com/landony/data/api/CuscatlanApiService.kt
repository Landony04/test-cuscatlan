package com.landony.data.api

import com.landony.data.api.models.PostsItemResponse
import retrofit2.Response
import retrofit2.http.GET

interface CuscatlanApiService {

    /**
     * Cuscatlan SERVICES
     * fun getAllPosts has service path
     */
    @GET(ApiRoutes.POSTS_GET_ALL)
    suspend fun getGitHubUsers(): Response<ArrayList<PostsItemResponse>>
}