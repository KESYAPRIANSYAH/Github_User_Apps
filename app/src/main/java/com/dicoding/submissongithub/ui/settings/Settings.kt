package com.dicoding.submissongithub.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissongithub.R
import com.dicoding.submissongithub.model.SettingsModel
import com.google.android.material.switchmaterial.SwitchMaterial

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val switchTheme = findViewById<SwitchMaterial>(R.id.theme)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingsModel = ViewModelProvider(this, ModeFactoryy(pref))[SettingsModel::class.java]
         settingsModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switchTheme.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            switchTheme.isChecked = false
        }
    }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingsModel.saveThemeSetting(isChecked)
        }
}


}