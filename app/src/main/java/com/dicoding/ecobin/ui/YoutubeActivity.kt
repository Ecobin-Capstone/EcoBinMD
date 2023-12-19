package com.dicoding.ecobin.ui


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecobin.R
import com.dicoding.ecobin.databinding.ActivityYoutubeBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class YoutubeActivity : AppCompatActivity(), LifecycleObserver {

    companion object {
        const val EXTRA_TYPE= ""
        var tipeSampah = ""
        var video_url = ""
    }
    private lateinit var binding: ActivityYoutubeBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
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

        youTubePlayerView = binding.youtubePlayerView
        youTubePlayerView.enableAutomaticInitialization = false
        lifecycle.addObserver(this)

//        tipeSampah= intent.getStringExtra(EXTRA_TYPE) ?: ""
//
//        lifecycleScope.launch {
//            try {
//                var successResponse = viewModel.linkYoutube(tipeSampah)
//                if (successResponse.message != "List video links of waste management for that waste type") {
//                    Log.d("URL",successResponse.data?.get(0)?.videoUrl.toString())
//                    video_url = successResponse.data?.get(0)?.videoUrl.toString()
//                }
//            } catch (e: HttpException) {
//                val errorBody = e.response()?.errorBody()?.string()
//                val errorResponse = Gson().fromJson(errorBody, LinkYoutubeResponse::class.java)
//                errorResponse.message?.let { showToast(it) }
//            }
//        }

//        val youtubeUrl = "YOUR_YOUTUBE_VIDEO_URL"
//        val videoId = extractVideoId(youtubeUrl)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        // Initialize the YouTube Player
        lifecycleScope.launch {
            val customPlayerUi: View =
            youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui)

            val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val customPlayerUiController =
                        CustomPlayerUiController(customPlayerUi, youTubePlayer, youTubePlayerView)
                    youTubePlayer.addListener(customPlayerUiController)
                    youTubePlayer.loadOrCueVideo(lifecycle = lifecycle,"nOEGsG1Oc7E",0f)
                }
            }

            val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
            youTubePlayerView.initialize(listener, options)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
        // Release the YouTube Player
        youTubePlayerView.release()
    }

    fun extractVideoId(youtubeUrl: String): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed/)[^#&?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(youtubeUrl)
        return if (matcher.find()) {
            matcher.group()
        } else {
            null
        }
    }
}