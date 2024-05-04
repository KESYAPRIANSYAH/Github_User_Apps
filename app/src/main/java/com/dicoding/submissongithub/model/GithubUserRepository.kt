package com.dicoding.submissongithub.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.dicoding.submissongithub.data.local.entity.UserFavoritEntity
import com.dicoding.submissongithub.data.local.room.GithubUserDao
import com.dicoding.submissongithub.data.remote.api.ApiService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GithubUserRepository(
    private val apiService: ApiService,
    private val githubuserdao: GithubUserDao,
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
)


{

    fun setSearchUser(keyword: String) = apiService.search(keyword)

    fun setDetailUser(username: String) = apiService.detailUser(username)

    fun setUserFollowers(username: String) = apiService.followers(username)

    fun setUserFollowing(username: String) = apiService.following(username)

    fun getAllFavoriteUser(): LiveData<List<UserFavoritEntity>> = githubuserdao.getFavorit()

    fun isFavoriteUser(username: String) = githubuserdao.getFavoriteUserByUsername(username)

    fun insertFavoriteUser(favorit: UserFavoritEntity) {
        executorService.execute { githubuserdao.addFavorite(favorit) }
    }

    fun deleteFavoriteUser(username: UserFavoritEntity) {
        executorService.execute { githubuserdao.deleteFavorit(username) }

    }
    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null

        fun getInstance(
            apiService: ApiService,
            githubuserdao: GithubUserDao
        ): GithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GithubUserRepository(apiService, githubuserdao)
            }.also { instance = it }
    }
}