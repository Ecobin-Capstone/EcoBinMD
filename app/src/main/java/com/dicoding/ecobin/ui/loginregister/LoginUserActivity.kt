package com.dicoding.ecobin.ui.loginregister

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.databinding.ActivityLoginUserBinding
import com.dicoding.ecobin.ui.Dashboard

class LoginUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))
        }
    }
}