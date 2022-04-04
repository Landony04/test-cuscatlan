package com.landony.data.api

import com.landony.data.api.models.PostsItemResponse
import com.landony.data.api.models.ResultCommentsResponse
import com.landony.data.api.models.ResultPhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CuscatlanApiService {

    /**
     * Cuscatlan SERVICES
     * fun getAllPosts has service path
     */
    @GET(ApiRoutes.POSTS_GET_ALL)
    suspend fun getGitHubUsers(): Response<ArrayList<PostsItemResponse>>

    /**
     * Cuscatlan SERVICES
     * fun getCommentsByPost has service path
     * @param idPost for apply filter to service
     */
    @GET(ApiRoutes.POSTS_GET_COMMENTS_BY_POST)
    suspend fun getCommentsByPost(@Path("idPost") idPost: String): Response<ArrayList<ResultCommentsResponse>>

    /**
     * Cuscatlan SERVICES
     * fun getPhotosByPost has service path
     * @param idPost for apply filter to service
     */
    @GET(ApiRoutes.POSTS_GET_IMAGES)
    suspend fun getPhotosByPost(@Path("idPost") idPost: String): Response<ArrayList<ResultPhotosResponse>>
}