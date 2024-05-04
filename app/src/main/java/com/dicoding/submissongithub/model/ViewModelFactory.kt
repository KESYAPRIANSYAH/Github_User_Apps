package com.dicoding.submissongithub.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissongithub.di.Injection

class ViewModelFactory private constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserModel::class.java)) {
            return UserModel(githubUserRepository) as T
        } else if (modelClass.isAssignableFrom(DetailUserModel::class.java)) {
            return DetailUserModel(githubUserRepository) as T
        } else if (modelClass.isAssignableFrom(FavoritModel::class.java)) {
            return FavoritModel(githubUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}