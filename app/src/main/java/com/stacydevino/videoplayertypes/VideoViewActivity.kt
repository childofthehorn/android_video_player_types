package com.stacydevino.videoplayertypes

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_videoview.*
import android.widget.MediaController


class VideoViewActivity : AppCompatActivity() {

    companion object {
        const val ARG_VIDEO_URL = "VideoView.URL"
    }

    private lateinit var videoUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoview)

        if (intent.extras == null || !intent.hasExtra(VideoViewActivity.ARG_VIDEO_URL)) {
            finish()
        }

        videoUrl = intent.getStringExtra(VideoViewActivity.ARG_VIDEO_URL).toString()

        videoView.setVideoURI(Uri.parse(videoUrl))

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
    }

    override fun onPause() {
        super.onPause()
        videoView.stopPlayback()
    }

    override fun onResume() {
        super.onResume()
        videoView.start()
    }

}