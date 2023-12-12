package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.R
import com.dicoding.ecobin.databinding.ActivityDashboardBinding
import com.dicoding.ecobin.ui.loginregister.LoginUserActivity

class Dashboard : AppCompatActivity() {
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        const val EXTRA_ID= "ID"
        var id = ""
    }

    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(EXTRA_ID) ?: ""
        Log.d("INI iD USER LOGIN", id)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginUserActivity::class.java))
                finish()
            }else{
                binding.nameLoggedIn.text = user.name
            }
            //kalau udah login bakal observe tabel user activity

        }

        binding.jemput.setOnClickListener {
            val homeIntent = Intent(this@Dashboard, ChooseWasteActivity::class.java)
            startActivity(homeIntent)
            true
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

        binding.logout.setOnClickListener {
            viewModel.logout()
        }
    }
}