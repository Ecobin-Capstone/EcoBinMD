package com.dicoding.ecobin.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.databinding.ActivityListVoucherBinding
import com.dicoding.ecobin.ui.adapter.ListVoucherAdapter
import kotlinx.coroutines.launch

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

    }
}