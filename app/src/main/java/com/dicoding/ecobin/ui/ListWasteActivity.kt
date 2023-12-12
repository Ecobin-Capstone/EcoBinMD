package com.dicoding.ecobin.ui

import android.os.Bundle
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

        lifecycleScope.launch {
            val organicWasteList = viewModelWaste.getOrganicWaste()
            val filteredList = organicWasteList.data?.filterNotNull() ?: emptyList()
            adapter.setListWaste(filteredList)
        }
    }

}