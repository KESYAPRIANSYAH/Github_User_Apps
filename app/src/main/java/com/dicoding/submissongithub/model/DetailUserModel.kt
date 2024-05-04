package com.dicoding.submissongithub.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissongithub.data.remote.api.Config
import com.dicoding.submissongithub.data.remote.response.DetatilUserResponse
import com.dicoding.submissongithub.data.remote.response.FollowResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {

    private val detailUser = MutableLiveData<DetatilUserResponse>()
    val getDeatailUserr: LiveData<DetatilUserResponse> = detailUser
    private val followingist = MutableLiveData<ArrayList<FollowResponse>>()
    val getFollowing: LiveData<ArrayList<FollowResponse>> = followingist
    private val followerist = MutableLiveData<ArrayList<FollowResponse>>()
    val getFollowers: LiveData<ArrayList<FollowResponse>> = followerist
    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading
    private val errorMessage = MutableLiveData<String>()
    val getErrorMessage: LiveData<String> = errorMessage

    fun setDetailUser(username: String) {
        isLoading.value = true
        val client = githubUserRepository.setDetailUser(username)
        client.enqueue(object : Callback<DetatilUserResponse> {
            override fun onResponse(
                call: Call<DetatilUserResponse>,
                response: Response<DetatilUserResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    detailUser.value = response.body()

                }
            }

            override fun onFailure(call: Call<DetatilUserResponse>, t: Throwable) {
                isLoading.value = true
                val error = "Terjadi kesalahan: ${t.message.toString()}"
                errorMessage.postValue(error)
            }

        })

    }

    fun setFollowing(username: String) {
        isLoading.value = true
        val client = githubUserRepository.setUserFollowing(username)
        client.enqueue(object :
            Callback<ArrayList<FollowResponse>> {
            override fun onResponse(
                call: Call<ArrayList<FollowResponse>>,
                response: Response<ArrayList<FollowResponse>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    followingist.value = response.body()

                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponse>>, t: Throwable) {
                isLoading.value = true
                val error = "Terjadi kesalahan: ${t.message.toString()}"
                errorMessage.postValue(error)
            }

        })

    }

    fun setFollower(username: String) {
        isLoading.value = true
        val client = githubUserRepository.setUserFollowers(username)
        client.enqueue(object : Callback<ArrayList<FollowResponse>> {
            override fun onResponse(
                call: Call<ArrayList<FollowResponse>>,
                response: Response<ArrayList<FollowResponse>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    followerist.value = response.body()

                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponse>>, t: Throwable) {
                isLoading.value = true
                val error = "Terjadi kesalahan: ${t.message.toString()}"
                errorMessage.postValue(error)
            }

        })

    }


}

