package com.dicoding.ecobin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.ecobin.data.repository.UserRepository
import com.dicoding.ecobin.pref.UserModel
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: UserRepository) : ViewModel(){
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}