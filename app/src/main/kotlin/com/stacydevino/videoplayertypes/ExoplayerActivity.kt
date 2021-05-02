package com.stacydevino.videoplayertypes

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.support.v4.media.session.MediaSessionCompat
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.stacydevino.videoplayertypes.databinding.ActivityExoplayerBinding


class ExoplayerActivity : AppCompatActivity() {

  companion object {
    const val ARG_VIDEO_URL = "ExoplayerActivity.URL"
    const val ARG_VIDEO_POSITION = "ExoplayerActivity.POSITION"
    const val ARG_VIDEO_URL_LIST = "ExoplayerActivity.URL_LIST"
  }

  private var videoUrl: String? = null
  private var videoUrlList: Array<String>? = null
  private lateinit var player: SimpleExoPlayer
  private var videoPosition: Long = 0L
  private lateinit var mediaSession: MediaSessionCompat
  private lateinit var fullscreenButton: ImageButton

  val layoutBinding by lazy { ActivityExoplayerBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutBinding.root)
    fullscreenButton = layoutBinding.playerView.findViewById(R.id.exo_fullscreen_button)

    if (intent.extras == null){
      finish()
    }

    videoUrl = intent.getStringExtra(ARG_VIDEO_URL)
    videoUrlList = intent.getStringArrayExtra(ARG_VIDEO_URL_LIST)

    savedInstanceState?.let { videoPosition = savedInstanceState.getLong(ARG_VIDEO_POSITION) }

    /* Adding a custom Layout with Button! */
    setFullscreenBtnImage(getOrientation())

    fullscreenButton.setOnClickListener {
      requestedOrientation = if (getOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
      } else {
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
      }
      //Do whatever the sensor wants after user moves phone position
      Handler(Looper.getMainLooper()).postDelayed(
          { requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR }, 5000)
    }
  }

  override fun onStart() {
    super.onStart()

    player = SimpleExoPlayer.Builder(this).build()

    layoutBinding.playerView.player = player

    videoUrl?.let { setSinglePlayerMedia() }

    //playlists
    videoUrlList?.let {
      player.setMediaSource(createPlaylist(it))
      player.prepare()
    }

    player.playWhenReady = true

    //Use Media Session Connector from the EXT library to enable MediaSession Controls in PIP.
    mediaSession = MediaSessionCompat(this, packageName)
    val mediaSessionConnector = MediaSessionConnector(mediaSession)
    mediaSessionConnector.setPlayer(player)
    mediaSession.isActive = true
  }

  private fun setSinglePlayerMedia(){
    val dataSourceFactory =
      DefaultDataSourceFactory(
          this,
          Util.getUserAgent(
              this,
              applicationInfo.loadLabel(packageManager)
                  .toString()
          )
      )

    val uri = Uri.parse(videoUrl)
    val mediaItem  = MediaItem.fromUri(uri)

    when (Util.inferContentType(Uri.parse(videoUrl))) {
      C.TYPE_HLS -> {
        val mediaSource = HlsMediaSource
          .Factory(dataSourceFactory)
          .createMediaSource(mediaItem)
        player.setMediaSource(mediaSource)
        player.prepare()
      }

      C.TYPE_OTHER -> {
        val mediaSource = ProgressiveMediaSource
          .Factory(dataSourceFactory)
          .createMediaSource(mediaItem)
        player.setMediaSource(mediaSource)
        player.prepare()
      }

      else -> {
        //This is to catch SmoothStreaming and
        //DASH types which we won't support currently, exit
        finish()
      }
    }
  }

  override fun onStop() {
    super.onStop()
    mediaSession.isActive = false
    layoutBinding.playerView.player = null
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

  /* Adding a custom Layout with Button! */

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    setFullscreenBtnImage(newConfig.orientation)
  }

  private fun getOrientation(): Int {
    return this.resources
        .configuration
        .orientation
  }

  private fun setFullscreenBtnImage(screenOrientation: Int) {
    when (screenOrientation) {
      Configuration.ORIENTATION_PORTRAIT -> {
        fullscreenButton.setImageResource(R.drawable.ic_fullscreen_white_24dp)
      }
      Configuration.ORIENTATION_LANDSCAPE -> {
        fullscreenButton.setImageResource(R.drawable.ic_fullscreen_exit_white_24dp)
      }
      Configuration.ORIENTATION_UNDEFINED -> {
        fullscreenButton.setImageResource(R.drawable.ic_fullscreen_exit_white_24dp)
      }
    }
  }


  /* Playlists of Videos */
  private fun createPlaylist(urlArray : Array<String>) : ConcatenatingMediaSource{

    val dataSourceFactory =
      DefaultDataSourceFactory(this,
          Util.getUserAgent(this,
              applicationInfo.loadLabel(packageManager)
                  .toString()))

    val mediaSourceList = ConcatenatingMediaSource()

    for (url in urlArray) {
      val uri = Uri.parse(url)
      val mediaItem  = MediaItem.fromUri(uri)
      when (Util.inferContentType(uri)) {
        C.TYPE_HLS -> {
          val mediaSource = HlsMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
          mediaSourceList.addMediaSource(mediaSource)
        }

        C.TYPE_OTHER -> {
          val mediaSource = ProgressiveMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
          mediaSourceList.addMediaSource(mediaSource)
        }

        else -> {
          //This is to catch SmoothStreaming and
          //DASH types which we won't support currently, just don't add it
        }
      }
    }
    return mediaSourceList
  }
}