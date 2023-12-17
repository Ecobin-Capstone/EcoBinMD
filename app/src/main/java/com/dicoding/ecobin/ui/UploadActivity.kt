package com.dicoding.ecobin.ui

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener{uploadImage()}
    }
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
    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            showLoading(true)
            val requestImageFile = imageFile.asRequestBody("image/jpg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            multipartBody?.let { part ->
                val requestBody = part.body

                Log.d("MultipartBody Details", "Name: ${part.headers}") // Change here

                // Extract file information
                Log.d("MultipartBody Details", "File Name: ${part.body?.contentType()}") // Change here
                Log.d("MultipartBody Details", "Content Type: ${requestBody?.contentType()}") // Change here
                Log.d("MultipartBody Details", "File Size: ${requestBody?.contentLength()} bytes") // Change here
            }
            Log.d("Multipart File", multipartBody.toString())
            Log.d("Request Details", "File Name: ${imageFile.name}, Content Type: image/jpeg")
            lifecycleScope.launch {
                try {
                    var successResponse = viewModelML.uploadImage(multipartBody)
                    showLoading(false)
                    if (successResponse.predictions != null) {
                        AlertDialog.Builder(this@UploadActivity).apply {
                            setTitle("Prediction Result")
                            setMessage(successResponse.predictions.toString())
                            setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            create()
                            show()
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
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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