package com.dicoding.ecobin.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecobin.data.response.LinkYoutubeResponse
import com.dicoding.ecobin.databinding.ActivityYoutubeBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class YoutubeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TYPE= ""
        var tipeSampah = ""
        var video_url = ""
    }
    private lateinit var binding: ActivityYoutubeBinding
    private val viewModel by viewModels<YoutubeViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tipeSampah= intent.getStringExtra(EXTRA_TYPE) ?: ""

        lifecycleScope.launch {
            try {
                var successResponse = viewModel.linkYoutube(tipeSampah)
                if (successResponse.message != "List video links of waste management for that waste type") {
                    Log.d("URL",successResponse.data?.get(0)?.videoUrl.toString())
                    video_url = successResponse.data?.get(0)?.videoUrl.toString()
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, LinkYoutubeResponse::class.java)
                errorResponse.message?.let { showToast(it) }
            }
        }
    }
}