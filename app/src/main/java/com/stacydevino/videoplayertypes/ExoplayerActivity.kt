package com.stacydevino.videoplayertypes


import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable.Orientation
import android.support.v4.media.session.MediaSessionCompat
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.OrientationEventListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_exoplayer.*
import kotlinx.android.synthetic.main.exoplayer_media_controls_custom.exo_fullscreen_button



class ExoplayerActivity : AppCompatActivity() {

    companion object {
        @JvmField
        val ARG_VIDEO_URL = "ExoplayerActivity.URL"
        val ARG_VIDEO_POSITION = "ExoplayerActivity.POSITION"
    }

    lateinit var mUrl: String
    lateinit var player : SimpleExoPlayer
    private var videoPosition : Long = 0L
    private lateinit var mediaSession: MediaSessionCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exoplayer)

        if (intent.extras == null || !intent.hasExtra(ARG_VIDEO_URL)) {
            finish()
        }

        mUrl = intent.getStringExtra(ARG_VIDEO_URL)

        savedInstanceState?.let { videoPosition = savedInstanceState.getLong(ARG_VIDEO_POSITION) }

        /* Adding a custom Layout with Button! */

        setFullscreenBtnImage(getOrientation())

        exo_fullscreen_button.setOnClickListener {
            if(getOrientation() == Configuration.ORIENTATION_LANDSCAPE){
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            } else {
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            }
            //Do whatever the sensor wants after user moves phone position
            Handler().postDelayed({setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR)}, 3000)
        }
    }


    override fun onStart() {
        super.onStart()


        player = ExoPlayerFactory
            .newSimpleInstance(this, DefaultTrackSelector())

        playerView.player = player

        val dataSourceFactory =
            DefaultDataSourceFactory(this,
                Util.getUserAgent(this,
                    applicationInfo.loadLabel(packageManager)
                        .toString()))

        when (Util.inferContentType(Uri.parse(mUrl))) {
            C.TYPE_HLS -> {
                val mediaSource = HlsMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mUrl))
                player.prepare(mediaSource)
            }

            C.TYPE_OTHER -> {
                val mediaSource = ExtractorMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mUrl))
                player.prepare(mediaSource)
            }

            else -> {
                //This is to catch SmoothStreaming and
                //DASH types which we won't support currently, exit
                finish()
            }
        }
        player.playWhenReady = true

        //Use Media Session Connector from the EXT library to enable MediaSession Controls in PIP.
        mediaSession = MediaSessionCompat(this, packageName)
        val mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlayer(player, null)
        mediaSession.isActive = true
    }


    override fun onStop() {
        super.onStop()
        mediaSession.isActive = false
        playerView.player = null
        player.release()
    }

    //Seek and Resume
    override fun onPause() {
        videoPosition = player.currentPosition
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (videoPosition > 0L) {
            player.seekTo(videoPosition)
        }
        player.playWhenReady = true
    }


    /* Adding a custom Layout with Button!  */

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setFullscreenBtnImage(newConfig.orientation)
    }

    private fun getOrientation() : Int {
        return this.getResources().getConfiguration().orientation
    }

    private fun setFullscreenBtnImage(screenOrientation: Int){
        when(screenOrientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                exo_fullscreen_button.setImageResource(R.drawable.ic_fullscreen_white_24dp)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                exo_fullscreen_button.setImageResource(R.drawable.ic_fullscreen_exit_white_24dp)
            }
            Configuration.ORIENTATION_UNDEFINED -> {
                exo_fullscreen_button.setImageResource(R.drawable.ic_fullscreen_exit_white_24dp)
            }
        }
    }
}