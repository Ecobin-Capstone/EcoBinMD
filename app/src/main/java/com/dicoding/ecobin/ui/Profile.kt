package com.dicoding.ecobin.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecobin.R
import com.dicoding.ecobin.data.response.ProfileResponse
import com.dicoding.ecobin.databinding.ActivityProfileBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val viewModelProfile by viewModels<ProfileViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    companion object {
        var ID = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.getSession().observe(this@Profile) { user ->
                user?.let {
                    binding.nameEditText.setText(user.name)
                    binding.phoneNumberEditText.setText(user.phoneNumber)
                    binding.emailEditText.setText(user.email)
                }
                ID = user.id
            }
        }

        binding.updateButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneNumberEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            lifecycleScope.launch {
                try {
                    var successResponse = viewModelProfile.updateProfile(ID,name,phone,email)
                    if (successResponse.message == "Your profile has been updated successfully") {
                        showLoading(false)
                        AlertDialog.Builder(this@Profile).apply {
                            setTitle("Berhasil!")
                            setMessage("Anda berhasil mengubah profile")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, Profile::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }else{
                        showLoading(false)
                        AlertDialog.Builder(this@Profile).apply {
                            setTitle("Gagal!")
                            setMessage("Pastikan semua kolom sudah terisi ya")
                            setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            create()
                            show()
                        }
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ProfileResponse::class.java)
                    showLoading(false)
                    showToast(errorResponse.message)
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val homeIntent = Intent(this@Profile, Dashboard::class.java)
                    startActivity(homeIntent)
                    true
                }

                R.id.scan -> {
                    val dashboardIntent = Intent(this@Profile, UploadActivity::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                R.id.history -> {
                    val dashboardIntent = Intent(this@Profile, History::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                R.id.profile -> {
                    val dashboardIntent = Intent(this@Profile, Profile::class.java)
                    startActivity(dashboardIntent)
                    true
                }
                // Add cases for other menu items if needed
                else -> false
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