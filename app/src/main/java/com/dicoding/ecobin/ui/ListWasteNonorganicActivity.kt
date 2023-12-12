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
import com.dicoding.ecobin.databinding.ActivityListWasteNonorganicBinding
import com.dicoding.ecobin.ui.adapter.ListWasteNOAdapter
import kotlinx.coroutines.launch

class ListWasteNonorganicActivity : AppCompatActivity() {
    private val viewModelWaste by viewModels<ListWasteViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityListWasteNonorganicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListWasteNonorganicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvWasteType.layoutManager = LinearLayoutManager(this)
        val adapter = ListWasteNOAdapter()
        binding.rvWasteType.adapter = adapter

        lifecycleScope.launch {
            val organicPartner = viewModelWaste.getNonOrganicPartner()
            val organicPartnerList = organicPartner.data?.filterNotNull() ?: emptyList()
            val partnerNames: List<String> = organicPartnerList.map { "${it.name} - ${it.subDistrict}" }

            val adapterSpinner = ArrayAdapter(this@ListWasteNonorganicActivity, R.layout.simple_spinner_item, partnerNames)
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

            val organicWasteList = viewModelWaste.getNonOrganicWaste()
            val filteredList = organicWasteList.data?.filterNotNull() ?: emptyList()
            adapter.setListWaste(filteredList)
        }
    }
}