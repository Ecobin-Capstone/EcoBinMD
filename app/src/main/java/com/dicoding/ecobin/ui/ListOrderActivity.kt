package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.databinding.ActivityListOrderBinding
import com.dicoding.ecobin.ui.adapter.ListOrderAdapter
import com.dicoding.ecobin.ui.loginregister.LoginMitraActivity
import kotlinx.coroutines.launch

class ListOrderActivity : AppCompatActivity() {
    private val viewModelOrder by viewModels<ListOrderViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        var id = ""
    }
    private lateinit var binding: ActivityListOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvOrder.layoutManager = LinearLayoutManager(this)
        val adapter = ListOrderAdapter()
        binding.rvOrder.adapter = adapter

        binding.logout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginMitraActivity::class.java))
                finish()
            } else {
                id = user.id
                Log.d("Ini id user", id)

                // Call the coroutine only if id is not null
                id?.let { userId ->
                    lifecycleScope.launch {
                        val order = viewModelOrder.getOrder(userId)
                        val filteredList = order.data?.filterNotNull() ?: emptyList()
                        adapter.setListOrder(filteredList)
                    }
                }
            }
        }

    }
}