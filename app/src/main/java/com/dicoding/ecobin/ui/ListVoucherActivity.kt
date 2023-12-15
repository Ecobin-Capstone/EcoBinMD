package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.data.response.RedeemResponse
import com.dicoding.ecobin.databinding.ActivityListVoucherBinding
import com.dicoding.ecobin.ui.adapter.ListVoucherAdapter
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ListVoucherActivity : AppCompatActivity(), ListVoucherAdapter.VoucherClickListener {
    private val viewModelOrder by viewModels<ListVoucherViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }
    companion object {
        var id = ""
    }
    private lateinit var binding: ActivityListVoucherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVoucherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvVoucher.layoutManager = LinearLayoutManager(this)
        val adapter = ListVoucherAdapter(this)
        binding.rvVoucher.adapter = adapter


        lifecycleScope.launch {
            viewModel.getSession().observe(this@ListVoucherActivity) { user ->
                user?.let {
                    id= user.id
                }
            }

            val voc = viewModelOrder.getVoucher()
            val vocList = voc.data?.filterNotNull() ?: emptyList()
            adapter.setListVoucher(vocList)

        }
    }

    override fun onRedeem(voucherID: Int) {
        lifecycleScope.launch {
            try {
                var successResponse = viewModelOrder.redeemPoint(voucherID,id.toInt())
                if (successResponse.message == "Congratulations, redeemption voucher was successfull") {
                    showLoading(false)
                    AlertDialog.Builder(this@ListVoucherActivity).apply {
                        setTitle("Berhasil!")
                        setMessage("Anda berhasil menukar voucher")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, Dashboard::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }else{
                    showLoading(false)
                    AlertDialog.Builder(this@ListVoucherActivity).apply {
                        setTitle("Gagal!")
                        setMessage("Point anda tidak mencukupi")
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
                showLoading(false)
                showToast(errorResponse.message)
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
}