package com.stacydevino.videoplayertypes

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  /* Sample public HLS Streams */
  var videoUrlArray = arrayOf(
      "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8",
      "https://content.jwplatform.com/manifests/yp34SRmf.m3u8",
      "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8",
      "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8",
      "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8",
      "http://www.streambox.fr/playlists/test_001/stream.m3u8"
  )

  var videoUrl = "https://content.jwplatform.com/manifests/yp34SRmf.m3u8"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    videoIntentBtn.setOnClickListener { onStartVideoIntent() }
    videoViewBtn.setOnClickListener { onStartVideoView() }
    exoplayerBtn.setOnClickListener { onStartExoplayerVideo() }
    exoplayerListBtn.setOnClickListener { onStartExoplayerVideoList() }
  }

  fun onStartVideoIntent() {
    val videoUri = Uri.parse(videoUrl)
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(videoUri, "video/*")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(intent)
  }

  fun onStartVideoView() {
    val intent = Intent(this, VideoViewActivity::class.java)
    intent.putExtra(VideoViewActivity.ARG_VIDEO_URL, videoUrl)
    startActivity(intent)
  }

  fun onStartExoplayerVideo() {
    val intent = Intent(this, ExoplayerActivity::class.java)
    intent.putExtra(ExoplayerActivity.ARG_VIDEO_URL, videoUrl)
    startActivity(intent)
  }

  fun onStartExoplayerVideoList() {
    val intent = Intent(this, ExoplayerActivity::class.java)
    intent.putExtra(ExoplayerActivity.ARG_VIDEO_URL_LIST, videoUrlArray)
    startActivity(intent)
  }

}
