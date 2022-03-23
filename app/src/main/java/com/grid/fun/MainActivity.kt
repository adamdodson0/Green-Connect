package com.grid.`fun`

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.grid.`fun`.databinding.ActivityMainBinding

/**
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Creates 3 mediaPlayers, All holding differing functions
    var mediaPlayer: MediaPlayer? = null
    var soundEffect1: MediaPlayer? = null
    var soundEffect2: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hides the action bar from user
        supportActionBar?.hide()

        // Creates media player which will play the main sound track
        // Sets it to start and loop continually
        mediaPlayer = MediaPlayer.create(this, R.raw.main)
        mediaPlayer!!.start()
        mediaPlayer!!.isLooping = true

        // Creates two media players for different sound effects
        // soundEffect1 is for the text click sounds and the square click sounds
        // soundEffect2 is for the sound after a level is beaten
        soundEffect1 = MediaPlayer.create(this, R.raw.click_sound)
        soundEffect2 = MediaPlayer.create(this, R.raw.click_sound)

        // Sets the soundEffects to release on completion
        soundEffect1!!.setOnCompletionListener {
            soundEffect1!!.release()
        }
        soundEffect2!!.setOnCompletionListener {
            soundEffect2!!.release()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * This function is called in all four fragment classes in onViewCreated. It will set
     * the muteUnmute onClickListener to either pause the mediaPlayer or start it.
     * (This mediaPlayer plays the main app music) It checks which action to perform based
     * on if the media player is currently playing. It also checks to flip the drawable to
     * either mute or unmute to signify the action to the user.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun setUpAudio(context: Context, muteUnmute: ImageButton) {
        // Changes drawable to mute or unmute
        if (mediaPlayer?.isPlaying == true) {
            muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.unmute, context.theme))
        } else {
            muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.mute, context.theme))
        }
        // Pauses or starts main sound
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

    /**
     * This function is used in all four fragment classes. It first checks if
     * the main mediaPlayer is playing (user has muted the game), if the main
     * mediaPlayer is paused it does nothing. If not, it will then check if the
     * effect being passed in is R.raw.level_win. This effect is strictly given to
     * mediaPlayer2. This is done so that the sound is not cut off immediately when
     * the user taps a button and another sound effect plays. If it is not level_win
     * the sound effect will play in soundEffect1 mediaPlayer.
     */
    fun playSoundEffect(context: Context, soundEffect: Int) {
        if (mediaPlayer?.isPlaying == true) {
            if (soundEffect == R.raw.level_win) {
                // Check if playing already, and stops and releases it if so
                if (soundEffect2?.isPlaying == true) {
                    soundEffect2!!.stop()
                    soundEffect2!!.release()
                }
                // Play passed in soundEffect (on soundEffect2)
                soundEffect2 = MediaPlayer.create(context, soundEffect)
                soundEffect2!!.start()
            } else {
                // Check if playing already, and stops and releases it if so
                if (soundEffect1?.isPlaying == true) {
                    soundEffect1!!.stop()
                    soundEffect1!!.release()
                }
                // Play passed in soundEffect (on soundEffect1)
                soundEffect1 = MediaPlayer.create(context, soundEffect)
                soundEffect1!!.start()
            }
        }
    }
}