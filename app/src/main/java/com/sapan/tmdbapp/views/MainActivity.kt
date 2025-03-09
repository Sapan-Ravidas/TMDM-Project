package com.sapan.tmdbapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding: ActivityMainBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HomeScreen.newInstance())
                        .commit()
                    true
                }
                R.id.navigation_bookmarks -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, BookMarkScreen.newInstance())
                        .commit()
                    true
                }
                else -> false
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, HomeScreen.newInstance())
            .commit()
    }
}