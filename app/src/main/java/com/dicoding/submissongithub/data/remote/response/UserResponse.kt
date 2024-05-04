package com.dicoding.submissongithub.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(


    @field:SerializedName("items")
    val items: ArrayList<ItemsItem>
)

data class ItemsItem(


    @field:SerializedName("login")
    val login: String,


    @field:SerializedName("avatar_url")
    val avatarUrl: String,



    )
