package com.dicoding.submissongithub.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissongithub.databinding.DataUserLayouteBinding
import com.dicoding.submissongithub.data.remote.response.FollowResponse


class FollowAdapter : RecyclerView.Adapter<FollowAdapter.UserViewHolder>() {
    private val userList = ArrayList<FollowResponse>()

    private var onUserItemClickListener: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onUserItemClickListener = onItemClickCallback
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(users: ArrayList<FollowResponse>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            DataUserLayouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onUserItemClickListener?.onItemClicked(user)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }



    inner class UserViewHolder(private val binding: DataUserLayouteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: FollowResponse) {

            binding.apply {
                Glide.with(itemView.context )
                    .load(user.avatarUrl)
                    .centerCrop()
                    .into(imgItemPhoto)

                tvItemName.text = user.login
                id.text = user.id.toString()

            }
        }
    }

    fun interface OnItemClickCallback {
        fun onItemClicked(data: FollowResponse)
    }
}