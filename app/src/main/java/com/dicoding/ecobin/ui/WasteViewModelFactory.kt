package com.dicoding.ecobin.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.injection.WasteInjection

class WasteViewModelFactory (private val repository: WasteRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListWasteViewModel::class.java) -> {
                ListWasteViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: WasteViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): WasteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(WasteViewModelFactory::class.java) {
                    INSTANCE = WasteViewModelFactory(WasteInjection.provideRepository(context))
                }
            }
            return INSTANCE as WasteViewModelFactory
        }
    }
}