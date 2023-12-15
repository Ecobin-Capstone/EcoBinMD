package com.dicoding.ecobin.ui

import android.R
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ecobin.data.response.RegisterResponse
import com.dicoding.ecobin.databinding.ActivityListWasteBinding
import com.dicoding.ecobin.ui.adapter.ListWasteTypeAdapter
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ListWasteActivity : AppCompatActivity() {
    private val viewModelWaste by viewModels<ListWasteViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityListWasteBinding

    companion object {
        var province = ""
        var subDistrict = ""
        var address = ""
        var postalCode = ""
        var village = ""
        var userID = 0
        var partnerID = 0
        var phoneNumber = ""
        var latitude = 0.0
        var longitude = 0.0
        var formattedDate = ""
        var formattedTime= ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListWasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvWasteType.layoutManager = LinearLayoutManager(this)
        val adapter = ListWasteTypeAdapter()
        binding.rvWasteType.adapter = adapter

        val currentDate = Date()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formattedDate = dateFormat.format(currentDate)

        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        formattedTime = timeFormat.format(currentDate)

        Log.d("MySQL Time Format", formattedTime)

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
                        address = addresses?.get(0)?.getAddressLine(0) ?: "No Address Found"
                        village = addresses!![0].locality
                        province = addresses!![0].adminArea
                        postalCode = addresses!![0].postalCode
                        subDistrict = addresses!![0].subAdminArea
                    }

                    phoneNumber = user.phoneNumber
                    userID = user.id.toInt()
                    latitude = user.lat
                    longitude = user.long
                    Log.d("Ini id user", userID.toString())
                    Log.d("Ini no hp", phoneNumber)
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
                    partnerID = organicPartner.data?.get(position)?.id!!
                    Log.d("Ini id partner", partnerID.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            val organicWasteList = viewModelWaste.getOrganicWaste()
            val filteredList = organicWasteList.data?.filterNotNull() ?: emptyList()
            adapter.setListWaste(filteredList)

        }

        binding.nextButton.setOnClickListener {
            val wasteItemList = adapter.getWasteItemList()
            val notes = binding.notes.text.toString()
            lifecycleScope.launch {
                try {
                    var successResponse = viewModelWaste.sendWaste(userID, partnerID, phoneNumber, province, subDistrict, village, postalCode, latitude,
                        longitude, address, formattedDate, formattedTime, notes, wasteItemList)
                    if (successResponse.message == "Waste pickup order has been successfully received by partner") {
                        showLoading(false)
                        AlertDialog.Builder(this@ListWasteActivity).apply {
                            setTitle("Yeah!")
                            setMessage("Terima kasih telah peduli dengan lingkungan! Estimasi poin yang didapat "+successResponse.estimatePointsEarned)
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, Dashboard::class.java)
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
                    val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                    showLoading(false)
                    showToast(errorResponse.message)
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
}