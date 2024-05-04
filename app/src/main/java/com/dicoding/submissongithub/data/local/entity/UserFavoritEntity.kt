package com.dicoding.submissongithub.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "favorite_users")
@Parcelize
data class UserFavoritEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("username")
    val username: String,
    @ColumnInfo("avatar_url")
    val avatarUrl: String,



    ) : Parcelable



