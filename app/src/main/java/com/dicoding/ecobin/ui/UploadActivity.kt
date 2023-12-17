package com.dicoding.ecobin.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecobin.data.response.ClassifierResponse
import com.dicoding.ecobin.data.response.LinkYoutubeResponse
import com.dicoding.ecobin.databinding.ActivityUploadBinding
import com.dicoding.ecobin.ui.helper.getImageUri
import com.dicoding.ecobin.ui.helper.reduceFileImage
import com.dicoding.ecobin.ui.helper.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class UploadActivity : AppCompatActivity() {
    private var currentImageUri: Uri? = null
    private lateinit var binding: ActivityUploadBinding
    companion object {
        var video_id = ""
        var wasteType=""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener{ uploadImage()}
    }

//    private fun uploadAndPerformSecondApiCall() {
//        uploadImage { success ->
//            if (success) {
//                performSecondAPICall(wasteType)
//            } else {
//                // Handle failure scenario if needed
//            }
//        }
//    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private val viewModelML by viewModels<UploadViewModel> {
        MLViewModelFactory.getInstance(this)
    }
    private val viewModel by viewModels<YoutubeViewModel> {
        WasteViewModelFactory.getInstance(this)
    }
    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            showLoading(true)
            val requestImageFile = imageFile.asRequestBody("image/jpg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    var successResponse = viewModelML.uploadImage(multipartBody)
                    showLoading(false)
                    if (successResponse.predictions != null) {
                        val resultMap = extractNonNullKeys(successResponse.predictions?.get(0).toString())

                        // Log all keys in the resultMap
                        resultMap.keys.forEach { key ->
                            Log.d("Key", key)
                        }

                        // Get the last key from the resultMap
                        val firstKey = resultMap.keys.firstOrNull()

                        firstKey?.let { key ->
                            AlertDialog.Builder(this@UploadActivity).apply {
                                setTitle("Success!")
                                setMessage("This is $key trash!")
                                setPositiveButton("Next") { _, _ ->
                                    val intent = Intent(context, YoutubeActivity::class.java)
                                    intent.putExtra(YoutubeActivity.EXTRA_TYPE, key)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ClassifierResponse::class.java)
                    showToast(errorResponse.predictions.toString())
                    showLoading(false)
                }
            }
        } ?: showToast("Silakan masukkan berkas gambar terlebih dahulu.")
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    fun performSecondAPICall(wasteType: String) {
        lifecycleScope.launch {
            try {
                var successResponse = viewModel.linkYoutube(wasteType)
                if (successResponse.message != "List video links of waste management for that waste type") {

                    AlertDialog.Builder(this@UploadActivity).apply {
                        setTitle("Prediction Result")
                        setMessage(successResponse.data?.get(0)?.videoUrl)
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, LinkYoutubeResponse::class.java)
                errorResponse.message?.let { showToast(it) }
                showLoading(false)
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun extractNonNullKeys(response: String?): Map<String, String> {
        val map = mutableMapOf<String, String>()

        val predictions = response?.split(",") // Split by ","
        if (predictions != null) {
            for (prediction in predictions) {
                val keyValue = prediction.split("=") // Split by "=" to separate key-value pairs
                if (keyValue.size == 2 && keyValue[1] != "null") { // Check for non-null values
                    val key = keyValue[0].substringAfter("(") // Extract key
                    val value = keyValue[1] // Store value as String
                    map[key] = value
                }
            }
        }
        return map
    }
    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }
    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }
}