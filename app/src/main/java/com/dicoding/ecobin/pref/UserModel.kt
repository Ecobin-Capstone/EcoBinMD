package com.dicoding.ecobin.pref
data class UserModel(
    val email: String,
    val id: String,
    val name: String,
    val isLogin: Boolean = false
)