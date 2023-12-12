package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecobin.R

class SplashActivity : AppCompatActivity() {
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                Handler().postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, SPLASH_DELAY)
            }else if(user.isLogin) {
                Handler().postDelayed({
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                    finish()
                }, SPLASH_DELAY)
            }
            //kalau udah login bakal observe tabel user activity
        }
    }
    companion object {
        private const val SPLASH_DELAY = 3000L // 3 seconds
    }
}