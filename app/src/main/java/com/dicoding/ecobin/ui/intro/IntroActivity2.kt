package com.dicoding.ecobin.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.databinding.ActivityIntro2Binding

class IntroActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityIntro2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            startActivity(Intent(this, IntroActivity3::class.java))
        }
    }
}