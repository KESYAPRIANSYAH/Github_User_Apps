package com.dicoding.submissongithub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissongithub.R
import com.dicoding.submissongithub.ui.adapter.UserAdapter
import com.dicoding.submissongithub.databinding.ActivityMainBinding
import com.dicoding.submissongithub.model.UserModel
import com.dicoding.submissongithub.model.SettingsModel
import com.dicoding.submissongithub.model.ViewModelFactory
import com.dicoding.submissongithub.ui.favorit.Favorite
import com.dicoding.submissongithub.ui.settings.ModeFactoryy
import com.dicoding.submissongithub.ui.settings.SettingPreferences
import com.dicoding.submissongithub.ui.settings.Settings
import com.dicoding.submissongithub.ui.settings.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var userAdapter: UserAdapter
    private var isDarkModeActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingsModel = ViewModelProvider(this, ModeFactoryy(pref))[SettingsModel::class.java]
        setupRecyclerView()
        setupSearchView()
        viewModel.getIsLoading.observe(this) {
            showLoading(it)
        }

        val initialUsername = "kesy"
        viewModel.setSearchUser(initialUsername)


        viewModel.getUser.observe(this) { userList ->
            if (userList != null) {
                userAdapter.setUserList(userList)
                showLoading(false)
            }
        }
        viewModel.getErrorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        settingsModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            this.isDarkModeActive = isDarkModeActive
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorit -> {
                val moveToFavorite = Intent(this, Favorite::class.java)
                startActivity(moveToFavorite)
            }

            R.id.action_settings -> {
                val moveToSettings = Intent(this, Settings::class.java)
                startActivity(moveToSettings)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val favoritItem = menu?.findItem(R.id.favorit)
        val settingsItem = menu?.findItem(R.id.action_settings)
        if (favoritItem != null && settingsItem != null) {

            if (isDarkModeActive) {
                favoritItem.setIcon(R.drawable.baseline_favorite_border_24_dark)
                settingsItem.setIcon(R.drawable.baseline_settings_24_dark)
            } else {
                favoritItem.setIcon(R.drawable.baseline_favorite_border_24)
                settingsItem.setIcon(R.drawable.baseline_settings_24)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.recycleclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarr.visibility = View.VISIBLE
        } else {
            binding.progressBarr.visibility = View.GONE
        }
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, actionId, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        performSearch(searchView.text.toString())
                        return@setOnEditorActionListener true
                    }
                    false
                }
        }
    }

    private fun performSearch(query: String) {
        showLoading(true)
        viewModel.setSearchUser(query)
    }
}