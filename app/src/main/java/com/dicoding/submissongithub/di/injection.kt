package com.dicoding.submissongithub.di

import android.content.Context
import com.dicoding.submissongithub.data.local.room.GithubDatabase
import com.dicoding.submissongithub.data.remote.api.Config
import com.dicoding.submissongithub.model.GithubUserRepository



object Injection {
    fun provideRepository(context: Context): GithubUserRepository {
        val apiService = Config.getApiService()
        val database = GithubDatabase.getDatabase(context)
        val dao = database.githubUserDao()
        return GithubUserRepository.getInstance(apiService, dao)
    }
}