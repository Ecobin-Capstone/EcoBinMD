package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecobin.R
import com.dicoding.ecobin.databinding.ActivityDashboardBinding
import com.dicoding.ecobin.ui.loginregister.LoginActivity
import kotlinx.coroutines.launch

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
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                binding.nameLoggedIn.text = user.name
                lifecycleScope.launch {
                    val activityUser = viewModel.getActivity(user.id)
                    binding.qtySend.text = activityUser.data?.get(0)?.sendWaste.toString() +" x"
                    binding.qtySuccess.text = activityUser.data?.get(0)?.managedWaste.toString() +" x"
                    binding.qtyPoint.text = activityUser.data?.get(0)?.point.toString()
                }
            }
        }

        binding.jemput.setOnClickListener {
            viewModel.getSession().removeObservers(this@Dashboard)
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