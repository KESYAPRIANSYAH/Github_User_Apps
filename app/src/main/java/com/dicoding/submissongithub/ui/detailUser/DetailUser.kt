package com.dicoding.submissongithub.ui.detailUser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide


import com.dicoding.submissongithub.R
import com.dicoding.submissongithub.data.local.entity.UserFavoritEntity
import com.dicoding.submissongithub.data.remote.response.DetatilUserResponse
import com.dicoding.submissongithub.ui.adapter.SectionAdapter
import com.dicoding.submissongithub.databinding.ActivityDetailUserBinding
import com.dicoding.submissongithub.model.DetailUserModel
import com.dicoding.submissongithub.model.FavoritModel
import com.dicoding.submissongithub.model.ViewModelFactory


import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(

            R.string.following, R.string.follower
        )}

    private val viewModel: DetailUserModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val favoriteModel by viewModels<FavoritModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userfavorit: UserFavoritEntity
    private var isFavorite = false
    private lateinit var binding: ActivityDetailUserBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = getString(R.string.profil)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
         val username = intent.getStringExtra(EXTRA_USERNAME).toString()
        if (savedInstanceState == null) {
            viewModel.setDetailUser(username)
        }
        viewModel.getIsLoading.observe(this, this::showLoading)
        viewModel.getErrorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }

        }

      viewModel.getDeatailUserr.observe(this) {
            setUser(it)
            userfavorit = UserFavoritEntity(
                it.login,
                it.avatarUrl,

            )
        }
        val sectionAdapter = SectionAdapter(this)
        sectionAdapter.username = username
        binding.viewpager1.adapter = sectionAdapter
        TabLayoutMediator(
            binding.tabs, binding.viewpager1
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        favoriteModel.isFavorite(username).observe(this) { favUser ->
            isFavorite = favUser != null
            changeIcon()
        }

     favoriteModel.isFavorite.observe(this) {
            isFavorite = it == true
         changeIcon()
        }

        binding.btnFavorite.setOnClickListener {
            if (!isFavorite) {
                favoriteModel.insertFavoriteUser(userfavorit)
                Toast.makeText(this, "Berhasil ditambahkan ke Favorit!", Toast.LENGTH_SHORT).show()
            } else {
                favoriteModel.deleteFavoriteUser(userfavorit)
                Toast.makeText(this, "Favorit berhasil dihapus.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setUser(detailUser: DetatilUserResponse?) {
        if (detailUser != null) {
            Glide.with(this)
                .load(detailUser.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.circleImageView)

            binding.viewName.text = detailUser.name
            binding.ViewUsername.text = detailUser.login
            binding.follower.text = detailUser.followers.toString()
            binding.following.text = detailUser.following.toString()
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun changeIcon() {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }




}







