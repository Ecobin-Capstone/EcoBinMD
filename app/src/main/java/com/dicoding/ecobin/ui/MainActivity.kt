package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.databinding.ActivityMainBinding
import com.dicoding.ecobin.ui.intro.IntroActivity2

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            startActivity(Intent(this, IntroActivity2::class.java))
        }
    }
}