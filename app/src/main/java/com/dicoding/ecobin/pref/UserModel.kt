package com.dicoding.ecobin.pref
data class UserModel(
    val phoneNumber: String,
    val email: String,
    val id: String,
    val name: String,
    val lat: Double,
    val long: Double,
    val isLogin: Boolean = false
)