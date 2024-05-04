package com.dicoding.submissongithub.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissongithub.data.local.entity.UserFavoritEntity

class FavoritModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getAllFavoriteUser() = githubUserRepository.getAllFavoriteUser()

    fun insertFavoriteUser(favorit: UserFavoritEntity) {
        githubUserRepository.insertFavoriteUser(favorit)
        _isFavorite.value = true
    }

    fun deleteFavoriteUser(favorit: UserFavoritEntity) {
       githubUserRepository.deleteFavoriteUser(favorit)
        _isFavorite.value = false
    }

    fun isFavorite(username: String) = githubUserRepository.isFavoriteUser(username)
}