package com.dicoding.ecobin.ui.loginregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ecobin.data.repository.UserRepository
import com.dicoding.ecobin.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: UserRepository) : ViewModel(){
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
    suspend fun loginUser(email: String, password: String)= repository.loginUser(email, password)
}