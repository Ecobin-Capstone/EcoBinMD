package com.dicoding.ecobin.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.databinding.ActivityListWasteBinding
import com.dicoding.ecobin.ui.adapter.ListWasteTypeAdapter
import com.dicoding.ecobin.ui.helper.ReverseGeocodingTask
import kotlinx.coroutines.launch

class ListWasteActivity : AppCompatActivity() {
    private val viewModelWaste by viewModels<ListWasteViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityListWasteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListWasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvWasteType.layoutManager = LinearLayoutManager(this)
        val adapter = ListWasteTypeAdapter()
        binding.rvWasteType.adapter = adapter

        lifecycleScope.launch {
            viewModel.getSession().observe(this@ListWasteActivity) { user ->
                user?.let {
                    if (it.isLogin) {
                        val apiKey = "AIzaSyAaWO6maHOcP2wjQSaEAbZ3nBc0lkz9OIY"
                        val latitude = it.lat
                        val longitude = it.long

                        val reverseGeocodingTask = ReverseGeocodingTask(apiKey, object : ReverseGeocodingTask.OnAddressFetchedListener {
                            override fun onAddressFetched(address: String) {
                                Log.d("Full Address", address)
                                // Handle the fetched address here
                            }
                        })

                        reverseGeocodingTask.execute(latitude, longitude)
                    }
                }
            }

            val organicPartner = viewModelWaste.getOrganicPartner()
            val organicPartnerList = organicPartner.data?.filterNotNull() ?: emptyList()
            val partnerNames: List<String> = organicPartnerList.map { "${it.name} - ${it.subDistrict}" }

            val adapterSpinner = ArrayAdapter(this@ListWasteActivity, R.layout.simple_spinner_item, partnerNames)
            adapterSpinner.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            binding.spinner.adapter = adapterSpinner

            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedWasteName = partnerNames[position]
                    // Lakukan sesuatu dengan nilai yang dipilih dari Spinner
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle jika tidak ada yang dipilih
                }
            }

            val organicWasteList = viewModelWaste.getOrganicWaste()
            val filteredList = organicWasteList.data?.filterNotNull() ?: emptyList()
            adapter.setListWaste(filteredList)

        }
    }

}