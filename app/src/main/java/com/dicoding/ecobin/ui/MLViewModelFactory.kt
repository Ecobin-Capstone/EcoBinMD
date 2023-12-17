package com.dicoding.ecobin.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.injection.MLInjection

class MLViewModelFactory (private val repository: WasteRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MLViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): MLViewModelFactory {
            if (INSTANCE == null) {
                synchronized(MLViewModelFactory::class.java) {
                    INSTANCE = MLViewModelFactory(MLInjection.provideRepository(context))
                }
            }
            return INSTANCE as MLViewModelFactory
        }
    }
}