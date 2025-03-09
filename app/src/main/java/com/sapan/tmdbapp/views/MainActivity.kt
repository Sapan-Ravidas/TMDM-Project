package com.sapan.tmdbapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, HomeScreen.newInstance())
            .commit()
    }
}