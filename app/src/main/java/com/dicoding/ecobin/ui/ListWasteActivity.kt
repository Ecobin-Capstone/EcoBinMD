package com.dicoding.ecobin.ui

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.databinding.ActivityListWasteBinding
import com.dicoding.ecobin.ui.adapter.ListWasteTypeAdapter
import kotlinx.coroutines.launch

class ListWasteActivity : AppCompatActivity() {
//    private var organicWasteList: List<DataOrganicWaste> = listOf()
    private val viewModelWaste by viewModels<ListWasteViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityListWasteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListWasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvWasteType.layoutManager = LinearLayoutManager(this)
        val adapter = ListWasteTypeAdapter()
        binding.rvWasteType.adapter = adapter

//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.home -> {
//                    val homeIntent = Intent(this@ListWasteActivity, Dashboard::class.java)
//                    startActivity(homeIntent)
//                    true
//                }
//
//                R.id.scan -> {
//                    val dashboardIntent = Intent(this@ListWasteActivity, UploadActivity::class.java)
//                    startActivity(dashboardIntent)
//                    true
//                }
//                R.id.history -> {
//                    val dashboardIntent = Intent(this@ListWasteActivity, History::class.java)
//                    startActivity(dashboardIntent)
//                    true
//                }
//                R.id.profile -> {
//                    val dashboardIntent = Intent(this@ListWasteActivity, Profile::class.java)
//                    startActivity(dashboardIntent)
//                    true
//                }
//                // Add cases for other menu items if needed
//                else -> false
//            }
//        }
        lifecycleScope.launch {
            val organicPartner = viewModelWaste.getOrganicPartner()
            val organicPartnerList = organicPartner.data?.filterNotNull() ?: emptyList()
            val partnerNames: List<String> = organicPartnerList.map { "${it.name} - ${it.subDistrict}" }

            // Buat ArrayAdapter
            val adapterSpinner = ArrayAdapter(this@ListWasteActivity, R.layout.simple_spinner_item, partnerNames)
            adapterSpinner.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            // Tetapkan ArrayAdapter ke Spinner
            binding.spinner.adapter = adapterSpinner

            // Tambahkan listener untuk menangani perubahan pilihan di Spinner
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