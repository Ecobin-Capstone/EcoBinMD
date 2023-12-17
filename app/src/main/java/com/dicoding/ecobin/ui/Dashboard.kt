package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.R
import com.dicoding.ecobin.data.response.RedeemResponse
import com.dicoding.ecobin.databinding.ActivityDashboardBinding
import com.dicoding.ecobin.ui.adapter.ListReceiptAdapter
import com.dicoding.ecobin.ui.loginregister.LoginActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class Dashboard : AppCompatActivity(), ListReceiptAdapter.VoucherClickListener {
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        var id = ""
    }
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvVoucher.layoutManager = LinearLayoutManager(this)
        val adapter = ListReceiptAdapter(this)
        binding.rvVoucher.adapter = adapter

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                binding.nameLoggedIn.text = user.name
                id= user.id
                lifecycleScope.launch {
                    val activityUser = viewModel.getActivity(user.id)
                    binding.qtySend.text = activityUser.data?.get(0)?.sendWaste.toString() +" x"
                    binding.qtySuccess.text = activityUser.data?.get(0)?.managedWaste.toString() +" x"
                    binding.qtyPoint.text = activityUser.data?.get(0)?.point.toString()

                    val order = viewModel.getReceipt(user.id)
                    val filteredList = order.data?.filterNotNull() ?: emptyList()
                    adapter.setListVoucher(filteredList)
                }
            }
        }

        binding.jemput.setOnClickListener {
            viewModel.getSession().removeObservers(this@Dashboard)
            val homeIntent = Intent(this@Dashboard, ChooseWasteActivity::class.java)
            startActivity(homeIntent)
            true
        }

        binding.gift.setOnClickListener {
            viewModel.getSession().removeObservers(this@Dashboard)
            val homeIntent = Intent(this@Dashboard, ListVoucherActivity::class.java)
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

    override fun checkCode(userID: String) {
        lifecycleScope.launch {
            try {
                var successResponse = viewModel.getReceipt(id)
                if (successResponse.message == "List of voucher receipts") {
                    AlertDialog.Builder(this@Dashboard).apply {
                        setTitle("Yeay!")
                        setMessage("Berikut kode anda ${successResponse.data?.get(0)?.exchangeToken}. Jangan tunjukkan ke siapapun!")
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RedeemResponse::class.java)
                showToast(errorResponse.message)
            }
        }
    }
    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}