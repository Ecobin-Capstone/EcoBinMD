package com.dicoding.ecobin.ui

import android.R
import android.location.Address
import android.location.Geocoder
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
import kotlinx.coroutines.launch
import java.util.Locale


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
                    val geocoder: Geocoder
                    val addresses: List<Address>?
                    geocoder = Geocoder(this@ListWasteActivity, Locale.getDefault())

                    addresses = geocoder.getFromLocation(
                        it.lat,
                        it.long,
                        1
                    )
                    if(addresses?.size != 0){
                        val address: String? = addresses?.get(0)?.getAddressLine(0) ?: "No Address Found"// If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                        val city: String = addresses!![0].locality
                        val state: String = addresses!![0].adminArea
                        val country: String = addresses!![0].countryName
                        val postalCode: String = addresses!![0].postalCode
                        val knownName: String = addresses!![0].featureName
                        Log.d("City", city)
                        Log.d("Address", address!!)
                        Log.d("State", state)
                        Log.d("Country", country)
                        Log.d("PostalCode", postalCode)
                        Log.d("knownName", knownName)
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