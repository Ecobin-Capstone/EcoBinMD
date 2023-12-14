package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.data.response.AcceptDeclineResponse
import com.dicoding.ecobin.databinding.ActivityListOrderBinding
import com.dicoding.ecobin.ui.adapter.ListOrderAdapter
import com.dicoding.ecobin.ui.loginregister.LoginActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ListOrderActivity : AppCompatActivity(), ListOrderAdapter.OrderClickListener {
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
        val adapter = ListOrderAdapter(this)
        binding.rvOrder.adapter = adapter

        binding.logout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                id = user.id
                Log.d("Ini id user", id)

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

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    override fun onAcceptOrder(orderId: Int) {
        lifecycleScope.launch {
            try {
                var successResponse = viewModelOrder.updateOrder(id,orderId)
                showToast(successResponse.message)
                if (successResponse.message == "Waste pickup order is being processed by partner") {
                    showLoading(false)
                    AlertDialog.Builder(this@ListOrderActivity).apply {
                        setTitle("Berhasil!")
                        setMessage("Anda berhasil menerima orderan")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, ListOrderActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, AcceptDeclineResponse::class.java)
                showLoading(false)
                showToast(errorResponse.message)
            }
        }
    }

    override fun onDeclineOrder(orderId: Int) {
        lifecycleScope.launch {
            try {
                var successResponse = viewModelOrder.declineOrder(id,orderId)
                showToast(successResponse.message)
                if (successResponse.message == "Waste pickup order has been declined by partner") {
                    showLoading(false)
                    AlertDialog.Builder(this@ListOrderActivity).apply {
                        setTitle("Yah!")
                        setMessage("Anda menolak orderan")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, ListOrderActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, AcceptDeclineResponse::class.java)
                showLoading(false)
                showToast(errorResponse.message)
            }
        }
    }
}