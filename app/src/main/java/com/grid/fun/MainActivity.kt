package com.grid.`fun`

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.ui.AppBarConfiguration
import com.grid.`fun`.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var mediaPlayer: MediaPlayer? = null
    var soundEffect1: MediaPlayer? = null
    var soundEffect2: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        supportActionBar?.hide()


        mediaPlayer = MediaPlayer.create(this, R.raw.main)
        mediaPlayer!!.start()
        mediaPlayer!!.isLooping = true

        soundEffect1 = MediaPlayer.create(this, R.raw.click_sound)
        soundEffect2 = MediaPlayer.create(this, R.raw.click_sound)

        soundEffect1!!.setOnCompletionListener {
            soundEffect1!!.release()
        }

        soundEffect2!!.setOnCompletionListener {
            soundEffect2!!.release()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setUpAudio(context: Context, muteUnmute: ImageButton) {
        if (mediaPlayer?.isPlaying == true) {
            muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.unmute, context.theme))
        } else {
            muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.mute, context.theme))
        }

        muteUnmute.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.mute, context.theme))
                playSoundEffect(context, R.raw.click_button)
                mediaPlayer?.pause()
            } else {
                muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.unmute, context.theme))
                mediaPlayer?.start()
                playSoundEffect(context, R.raw.click_button)
            }
        }
    }

    fun playSoundEffect(context: Context, soundEffect: Int) {
        if (soundEffect == R.raw.level_win) {
            if (mediaPlayer?.isPlaying == true) {
                if (soundEffect2?.isPlaying == true) {
                    soundEffect2!!.stop()
                    soundEffect2!!.release()
                }
                soundEffect2 = MediaPlayer.create(context, soundEffect)
                soundEffect2!!.start()
            }
        } else if (mediaPlayer?.isPlaying == true) {
            if (soundEffect1?.isPlaying == true) {
                soundEffect1!!.stop()
                soundEffect1!!.release()
            }
            soundEffect1 = MediaPlayer.create(context, soundEffect)
            soundEffect1!!.start()
        }
    }
}