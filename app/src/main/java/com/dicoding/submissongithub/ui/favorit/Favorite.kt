package com.dicoding.submissongithub.ui.favorit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissongithub.R
import com.dicoding.submissongithub.data.remote.response.ItemsItem
import com.dicoding.submissongithub.databinding.ActivityFavoriteBinding
import com.dicoding.submissongithub.model.FavoritModel
import com.dicoding.submissongithub.model.ViewModelFactory
import com.dicoding.submissongithub.ui.adapter.UserAdapter

class Favorite : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoritModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = getString(R.string.userFavorit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycleclerview.layoutManager = LinearLayoutManager(this)

        viewModel.getAllFavoriteUser().observe(this) { favUsers ->
            if (favUsers != null) {
                val items = arrayListOf<ItemsItem>()
                favUsers.map {
                    val item = ItemsItem(it.username, it.avatarUrl)
                    items.add(item)
                }
                binding.recycleclerview.adapter = UserAdapter(items)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}