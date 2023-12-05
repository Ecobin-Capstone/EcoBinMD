package com.dicoding.ecobin.ui.loginregister

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.databinding.ActivitySelectLoginBinding

class SelectLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signinUser.setOnClickListener {
            startActivity(Intent(this, LoginUserActivity::class.java))
        }

        binding.signinMitra.setOnClickListener {
            startActivity(Intent(this, LoginMitraActivity::class.java))
        }
    }
}