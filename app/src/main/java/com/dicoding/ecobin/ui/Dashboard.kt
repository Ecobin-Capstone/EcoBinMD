package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.R
import com.dicoding.ecobin.databinding.ActivityDashboardBinding
import com.dicoding.ecobin.ui.loginregister.LoginUserActivity

class Dashboard : AppCompatActivity() {
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginUserActivity::class.java))
                finish()
            }
            //kalau udah login bakal observe tabel user activity
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val homeIntent = Intent(this@Dashboard, Dashboard::class.java)
                    startActivity(homeIntent)
                    true
                }

                R.id.scan -> {
                    val dashboardIntent = Intent(this@Dashboard, UploadActivity::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                R.id.history -> {
                    val dashboardIntent = Intent(this@Dashboard, History::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                R.id.profile -> {
                    val dashboardIntent = Intent(this@Dashboard, Profile::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                // Add cases for other menu items if needed
                else -> false
            }
        }
    }
}