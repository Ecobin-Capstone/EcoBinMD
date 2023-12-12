package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.databinding.ActivityChooseWasteBinding

class ChooseWasteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseWasteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseWasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.organik.setOnClickListener {
            val homeIntent = Intent(this@ChooseWasteActivity, ListWasteActivity::class.java)
            startActivity(homeIntent)
            true
        }
        binding.nonorganik.setOnClickListener {
            val homeIntent = Intent(this@ChooseWasteActivity, ListWasteNonorganicActivity::class.java)
            startActivity(homeIntent)
            true
        }
    }
}