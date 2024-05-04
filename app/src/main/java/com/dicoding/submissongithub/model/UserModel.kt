package com.dicoding.submissongithub.model

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissongithub.data.remote.api.Config
import com.dicoding.submissongithub.data.remote.response.ItemsItem
import com.dicoding.submissongithub.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserModel (private val githubUserRepository: GithubUserRepository):ViewModel(){

    private  val userlist = MutableLiveData<ArrayList<ItemsItem>>()
    val getUser: LiveData<ArrayList<ItemsItem>> = userlist
    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading
    private val errorMessage = MutableLiveData<String>()
    val getErrorMessage: LiveData<String> = errorMessage

    fun setSearchUser(user:String){
        isLoading.value = true
        val client = githubUserRepository.setSearchUser(user)
       client.enqueue(object  : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                isLoading.value = false
             if (response.isSuccessful){
                 userlist.value = response.body()?.items
             }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                isLoading.value = true
                val error = "Terjadi kesalahan: ${t.message.toString()}"
                errorMessage.postValue(error)
            }

        })

    }


    companion object {
        private const val TAG = "MainViewModel"
    }



}

