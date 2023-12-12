package com.dicoding.ecobin.ui.loginregister

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecobin.data.response.RegisterResponse
import com.dicoding.ecobin.databinding.ActivityLoginUserBinding
import com.dicoding.ecobin.pref.UserModel
import com.dicoding.ecobin.ui.Dashboard
import com.dicoding.ecobin.ui.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginUserActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.loginButton.setOnClickListener {
//            startActivity(Intent(this, Dashboard::class.java))
//        }
        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            showLoading(true)
            lifecycleScope.launch {
                try {
                    var successResponse = viewModel.loginUser(email, password)
                    showToast(successResponse.message)
                    if (successResponse != null) {
                        showLoading(false)
                        viewModel.saveSession(UserModel(email))
                        AlertDialog.Builder(this@LoginUserActivity).apply {
                            setTitle("Yeah!")
                            setMessage("Anda berhasil login.")
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
}