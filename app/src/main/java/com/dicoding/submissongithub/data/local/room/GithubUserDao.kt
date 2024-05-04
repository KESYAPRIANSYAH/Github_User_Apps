package com.dicoding.submissongithub.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submissongithub.data.local.entity.UserFavoritEntity

@Dao
interface GithubUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favorite: UserFavoritEntity)
    @Query("SELECT * FROM favorite_users WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<UserFavoritEntity>

    @Query("SELECT * FROM favorite_users")
    fun getFavorit(): LiveData<List<UserFavoritEntity>>
    @Delete
    fun deleteFavorit(favorite: UserFavoritEntity)
}
