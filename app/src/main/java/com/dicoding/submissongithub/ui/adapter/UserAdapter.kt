package com.dicoding.submissongithub.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissongithub.databinding.DataUserLayouteBinding


import com.dicoding.submissongithub.data.remote.response.ItemsItem
import com.dicoding.submissongithub.ui.detailUser.DetailUser

class UserAdapter(
    private val userList: MutableList<ItemsItem> = mutableListOf(),

    ) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(users: ArrayList<ItemsItem>) {
        this.userList.clear()
        this.userList.addAll(users)
        notifyDataSetChanged()
    }


    inner class UserViewHolder(private val binding: DataUserLayouteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ItemsItem) {

            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .into(imgItemPhoto)

                tvItemName.text = user.login

            }
            itemView.setOnClickListener { view ->
                val toDetail = Intent(view.context, DetailUser::class.java)
                toDetail.putExtra(DetailUser.EXTRA_USERNAME, user.login)
                view.context.startActivity(toDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            DataUserLayouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}