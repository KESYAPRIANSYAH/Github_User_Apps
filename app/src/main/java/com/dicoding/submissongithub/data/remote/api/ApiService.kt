package com.dicoding.submissongithub.data.remote.api


import com.dicoding.submissongithub.data.remote.response.DetatilUserResponse
import com.dicoding.submissongithub.data.remote.response.FollowResponse


import com.dicoding.submissongithub.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun search(
        @Query("q") query: String
    ): Call  <UserResponse>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ): Call<DetatilUserResponse>
    @GET("users/{username}/followers")
    fun followers(
        @Path("username") username: String
    ): Call<ArrayList<FollowResponse>>

    @GET("users/{username}/following")
    fun following(
        @Path("username") username: String
    ): Call<ArrayList<FollowResponse>>
}