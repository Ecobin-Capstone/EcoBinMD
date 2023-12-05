package com.dicoding.ecobin.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.databinding.ActivityIntro3Binding

class IntroActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityIntro3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            startActivity(Intent(this, IntroActivity4::class.java))
        }
    }
}