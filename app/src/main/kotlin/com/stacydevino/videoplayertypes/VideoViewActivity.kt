package com.stacydevino.videoplayertypes

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.MediaController
import com.stacydevino.videoplayertypes.databinding.ActivityVideoviewBinding


class VideoViewActivity : AppCompatActivity() {

    companion object {
        const val ARG_VIDEO_URL = "VideoView.URL"
    }

    private val layoutBinding by lazy { ActivityVideoviewBinding.inflate(layoutInflater) }

    private lateinit var videoUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)

        if (intent.extras == null || !intent.hasExtra(ARG_VIDEO_URL)) {
            finish()
        }

        videoUrl = intent.getStringExtra(ARG_VIDEO_URL).toString()

        layoutBinding.videoView.setVideoURI(Uri.parse(videoUrl))

        val mediaController = MediaController(this)
        mediaController.setAnchorView(layoutBinding.videoView)
        layoutBinding.videoView.setMediaController(mediaController)
    }

    override fun onPause() {
        super.onPause()
        layoutBinding.videoView.stopPlayback()
    }

    override fun onResume() {
        super.onResume()
        layoutBinding.videoView.start()
    }

}