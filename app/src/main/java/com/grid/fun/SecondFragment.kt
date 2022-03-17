package com.grid.`fun`

import android.annotation.SuppressLint
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.grid.`fun`.databinding.FragmentSecondBinding

/**
 * This class is the game fragment. The game consists of 25 image buttons centered in a grid,
 * each with either red or green color. The goal of the game is to match all of the image buttons
 * to the green color. When the user taps an image button, all the image buttons in a 1 box radius
 * change color, including the image button that is clicked. there are 10 levels in total. The level
 * is kept track of on the top right and is passed to the next fragment to show to the user when they
 * either win or lose. There is also a text view explaining how many clicks the user has to get the
 * image buttons matching. If they fail to match the image buttons with the correct amount of clicks,
 * it takes them to the next fragment (third).
 */
class SecondFragment : Fragment(), View.OnClickListener {

    // Holds the amount of clicks left, displayed on screen in textView
    var clicksLeft: Int = 0
    // Holds the level number, displayed on screen in TextView
    var level: Int = 0
    // Holds the number of hearts/lives left, displayed on screen in imageView
    var heartsNum: Int = 3
    // Holds all 25 imageButtons for the board to be played on
    lateinit var imageButtons: List<ImageButton>
    // Variable holds binding
    private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    /**
     * This class sets actions on the views creation.
     *
     * @return binding.root
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * This class sets the text on screen as well as setting all the on click listeners
     * for each image button.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sends user data to next fragment (third)
        val bundle = Bundle()

        // Creates listOf all 25 of the imageButtons in the center of the screen that make up the game
        imageButtons = listOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
            binding.imageButton5, binding.imageButton6, binding.imageButton7, binding.imageButton8, binding.imageButton9,
            binding.imageButton10, binding.imageButton11, binding.imageButton12, binding.imageButton13,
            binding.imageButton14, binding.imageButton15, binding.imageButton16, binding.imageButton17,
            binding.imageButton18, binding.imageButton19, binding.imageButton20, binding.imageButton21,
            binding.imageButton22, binding.imageButton23, binding.imageButton24, binding.imageButton25)

        // Grabs bundle from first fragment to check if player has beaten the game
        var beatGame: Int? = arguments?.getInt("beatGame")
        // Set trophy to visible to show user beat the game before
        if (beatGame == 1) {
            binding.trophy.visibility = View.VISIBLE
        }

        // Sets up the first levels image buttons, clicksLeft, and levelText
        startNextLevel(bundle, false)

        // Sets up the audio for the second fragment
        (activity as MainActivity).setUpAudio(requireContext(), binding.muteUnmute)

        // Submit button for user, checks if buttons all have same drawable resource, if they do, continue to
        // next level on same fragment. If not, send to next fragment.
        binding.submitButton.setOnClickListener {
            submit(bundle)
        }

        /**
         * Gives each imageButton click effect, passes a number to show position of button
         * When button is clicked, the button and the buttons around (effect within 1 button radius)
         * it all change image.
         */
        imageButtons.forEach() {
            it.setOnClickListener(this)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun submit(bundle: Bundle) {
        if (imageButtons.all {it.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState}) {
            // Next level is initiated (in same fragment)
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.level_win)
            startNextLevel(bundle, false)
        } else { // User lost the game, sent to next fragment (third)
            heartsNum -= 1
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.damage)
            when (heartsNum) {
                0 -> {
                    bundle.putInt("level", level)
                    binding.submitButton.visibility = View.INVISIBLE
                    findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
                }
                1 -> {
                    binding.hearts.setImageDrawable(resources.getDrawable(R.drawable.hearts_1, requireContext().theme))
                    startNextLevel(bundle, true)
                }
                2 -> {
                    binding.hearts.setImageDrawable(resources.getDrawable(R.drawable.hearts_2, requireContext().theme))
                    startNextLevel(bundle, true)
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setUpAudio() {
        if ((activity as MainActivity).mediaPlayer?.isPlaying == true) {
            binding.muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.unmute, requireContext().theme))
        } else {
            binding.muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.mute, requireContext().theme))
        }

        binding.muteUnmute.setOnClickListener {
            if ((activity as MainActivity).mediaPlayer?.isPlaying == true) {
                binding.muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.mute, requireContext().theme))
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
                (activity as MainActivity).mediaPlayer?.pause()
            } else {
                binding.muteUnmute.setImageDrawable(resources.getDrawable(R.drawable.unmute, requireContext().theme))
                (activity as MainActivity).mediaPlayer?.start()
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
            }
        }
    }

    /**
     * This function checks the imageButtons drawable image and changes it to the opposite
     * image for the puzzle board.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun flipOver(imageButtons: List<ImageButton>, array: IntArray, settingUp: Boolean) {
        array.forEach {
            if (imageButtons[it - 1].drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState) {
                imageButtons[it - 1].setImageDrawable(resources.getDrawable(R.drawable.square1, requireContext().theme))
            } else {
                imageButtons[it - 1].setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            }
        }
        if (!settingUp) {
            clicksLeft -= 1
            if (clicksLeft >= 0)
                binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
            if (clicksLeft == 0) {
                binding.submitButton.visibility = View.INVISIBLE
                clickable(false)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.submitButton.visibility = View.VISIBLE
                    binding.submitButton.performClick()
                    clickable(true)
                }, 1000)
            }
        }
    }

    /**
     * This function increases the level count by 1, changes the click amount to the value for
     * the appropriate level, and turns the button resources to the correct values.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun startNextLevel(bundle: Bundle, restart: Boolean) {
        if (!restart) {
            level += 1
            binding.levelText.text = getString(R.string.level, level)
        } else {
            resetGrid()
        } // Do nothing
        when (level) {
            1 -> levels((1..5).random(), 4, 1)
            2 -> levels((1..5).random(), 4, 2)
            3 -> levels((1..5).random(), 4, 3)
            4 -> levels((1..5).random(), 4, 4)
            5 -> levels((1..5).random(), 4, 5)
            6 -> levels((1..5).random(), 5, 6)
            7 -> levels((1..5).random(), 5, 7)
            8 -> levels((1..5).random(), 5, 8)
            9 -> levels((1..5).random(), 5, 9)
            10 -> levels((1..5).random(), 6, 10)
            else -> {
                bundle.putInt("level", level)
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.win)
                findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
            }
        }
    }

    fun levels(random: Int, clicks: Int, levelNumber: Int) {
        when (levelNumber) {
            1 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(2, 3, 4, 6, 8, 10, 11, 12, 14, 15, 16, 18, 20, 22, 23, 24), true)
                    2 -> flipOver(imageButtons, intArrayOf(1, 2, 4, 5, 6, 7, 9, 10, 16, 17, 19, 20, 21, 22, 24, 25), true)
                    3 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 24, 25), true)
                    4 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 4, 5, 6, 7, 8,  9, 10, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25), true)
                    5 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25), true)
                }
            }
            2 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(1,2,4, 5, 6, 8, 10, 12, 13, 14, 22, 23, 24), true)
                    2 -> flipOver(imageButtons, intArrayOf(2, 3, 4, 7, 8, 9, 11, 13, 15, 16, 18, 20, 21, 23, 25), true)
                    3 -> flipOver(imageButtons, intArrayOf(1, 2, 4, 5, 6, 7, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25), true)
                    4 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 23, 24, 25), true)
                    5 -> flipOver(imageButtons, intArrayOf(11, 12, 13, 14, 15), true)
                }
            }
            3 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(3, 8, 11, 12, 14, 15, 18, 23), true)
                    2 -> flipOver(imageButtons, intArrayOf(3, 4, 5, 7, 10, 11, 13, 15, 16, 20, 21, 22, 23, 24, 25), true)
                    3 -> flipOver(imageButtons, intArrayOf(4, 5, 6, 7, 11, 12, 14, 15, 19, 20, 21, 22), true)
                    4 -> flipOver(imageButtons, intArrayOf(1, 2, 8, 9, 10, 11, 12, 13, 14, 15, 18, 19, 20, 21, 22), true)
                    5 -> flipOver(imageButtons, intArrayOf(1, 2, 4, 5, 6, 7, 9, 10, 13), true)
                }
            }
            4 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(6, 8, 10, 11, 13, 15, 16, 17, 19, 20, 22, 23, 24), true)
                    2 -> flipOver(imageButtons, intArrayOf(3, 4, 5, 6, 7, 8, 11, 12, 14, 15, 19, 20, 21, 22), true)
                    3 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 6, 9, 12, 15, 16, 18, 20, 21, 22, 24, 25), true)
                    4 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 18, 19, 20, 21, 22, 24, 25), true)
                    5 -> flipOver(imageButtons, intArrayOf(6, 7, 9, 10, 11, 12, 13, 16, 17, 19, 20), true)
                }
            }
            5 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 18, 21, 22, 23), true)
                    2 -> flipOver(imageButtons, intArrayOf(9,10, 11, 12, 13, 14, 15, 18, 23, 24, 25), true)
                    3 -> flipOver(imageButtons, intArrayOf(2, 3, 5, 7, 9, 13, 18, 24, 25), true)
                    4 -> flipOver(imageButtons, intArrayOf(2, 3, 4, 9, 10, 12, 13, 15, 19, 20, 22, 23, 24), true)
                    5 -> flipOver(imageButtons, intArrayOf(1, 3, 5, 6, 8, 10, 12, 13, 15, 17, 18, 19, 22, 23, 24), true)
                }
            }
            6 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 6, 7, 8, 9, 10, 13, 14, 15, 24, 25), true)
                    2 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 6, 7, 9, 10, 13, 14, 15, 23, 24, 25), true)
                    3 -> flipOver(imageButtons, intArrayOf(1, 4, 7, 8, 10, 11, 15, 16, 20, 22, 23, 24), true)
                    4 -> flipOver(imageButtons, intArrayOf(4, 5, 9, 10, 11, 12, 13, 17, 18, 20, 22, 23, 25), true)
                    5 -> flipOver(imageButtons, intArrayOf(1, 3, 4, 6, 8, 10, 11, 13, 15, 16, 18, 20, 21, 23, 24), true)
                }
            }
            7 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(3, 4, 5, 6, 7, 8, 11, 12, 13, 16, 17, 18, 19, 20, 23), true)
                    2 -> flipOver(imageButtons, intArrayOf(1, 2, 4, 5, 6, 7, 11, 12, 18, 19, 20, 23), true)
                    3 -> flipOver(imageButtons, intArrayOf(1, 2, 7, 10, 12, 15, 16, 18, 19, 23, 24, 25), true)
                    4 -> flipOver(imageButtons, intArrayOf(2, 5, 7, 10, 12, 13, 15, 16, 17, 21, 22), true)
                    5 -> flipOver(imageButtons, intArrayOf(1, 4, 6, 9, 11, 12, 14, 15, 16, 17, 21, 22), true)
                }
            }
            8 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(1, 3, 5, 6, 9, 11, 12, 13, 16, 17, 18, 19, 20, 21, 22), true)
                    2 -> flipOver(imageButtons, intArrayOf(1, 2, 4, 5, 6, 8, 9, 11, 15, 17, 18, 19, 24, 25), true)
                    3 -> flipOver(imageButtons, intArrayOf(2, 4, 7, 9, 13, 14, 15, 16, 18, 19, 21, 23, 24), true)
                    4 -> flipOver(imageButtons, intArrayOf(2, 4, 6, 10, 12, 14, 24, 25), true)
                    5 -> flipOver(imageButtons, intArrayOf(3, 6, 7, 16, 17, 23), true)
                }
            }
            9 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(4, 5, 7, 10, 12, 15, 17, 18, 20, 23, 24, 25), true)
                    2 -> flipOver(imageButtons, intArrayOf(2, 4, 7, 9, 11, 15, 16, 19, 21, 24), true)
                    3 -> flipOver(imageButtons, intArrayOf(7, 8, 10, 14, 15, 16, 17, 21, 23, 25), true)
                    4 -> flipOver(imageButtons, intArrayOf(2, 5, 6, 9, 12, 13, 15, 19, 20, 21, 22), true)
                    5 -> flipOver(imageButtons, intArrayOf(8, 11, 12, 13, 14, 15, 19, 20, 23, 24, 25), true)
                }
            }
            10 -> {
                when (random) {
                    1 -> flipOver(imageButtons, intArrayOf(1, 2, 6, 7, 13, 16, 17, 21, 22), true)
                    2 -> flipOver(imageButtons, intArrayOf(1, 2, 9, 10, 11, 12, 13, 19, 20, 21, 22), true)
                    3 -> flipOver(imageButtons, intArrayOf(1, 5, 7, 8, 9, 16, 20, 22, 23, 24), true)
                    4 -> flipOver(imageButtons, intArrayOf(1, 2, 6, 8, 10, 12, 14, 17, 18, 19, 24, 25), true)
                    5 -> flipOver(imageButtons, intArrayOf(1, 3, 4, 8, 14, 15, 17, 18, 20, 21, 22, 23, 24, 25), true)
                }
            }
        }
        clicksLeft = clicks
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     *
     */
    private fun clickable(clickable: Boolean) {
        imageButtons.forEach() {
            it.isClickable = clickable
        }
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level one
     * The level is randomly chosen from three different potential levels.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun resetGrid() {
        imageButtons.forEach() {
            it.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imageButton1 -> flipOver(imageButtons, intArrayOf(1, 2, 6, 7), false)
            R.id.imageButton2 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 6, 7, 8), false)
            R.id.imageButton3 -> flipOver(imageButtons, intArrayOf(2, 3, 4, 7, 8, 9), false)
            R.id.imageButton4 -> flipOver(imageButtons, intArrayOf(3, 4, 5, 8, 9, 10), false)
            R.id.imageButton5 -> flipOver(imageButtons, intArrayOf(4, 5, 9, 10), false)
            R.id.imageButton6 -> flipOver(imageButtons, intArrayOf(1, 2, 6, 7, 11, 12), false)
            R.id.imageButton7 -> flipOver(imageButtons, intArrayOf(1, 2, 3, 6, 7, 8, 11, 12, 13), false)
            R.id.imageButton8 -> flipOver(imageButtons, intArrayOf(2, 3, 4, 7, 8, 9, 12, 13, 14), false)
            R.id.imageButton9 -> flipOver(imageButtons, intArrayOf(3, 4, 5, 8, 9, 10, 13, 14, 15), false)
            R.id.imageButton10 -> flipOver(imageButtons, intArrayOf(4, 5, 9, 10, 14, 15), false)
            R.id.imageButton11 -> flipOver(imageButtons, intArrayOf(6, 7, 11, 12, 16, 17), false)
            R.id.imageButton12 -> flipOver(imageButtons, intArrayOf(6, 7, 8, 11, 12, 13, 16, 17, 18), false)
            R.id.imageButton13 -> flipOver(imageButtons, intArrayOf(7, 8, 9, 12, 13, 14, 17, 18, 19), false)
            R.id.imageButton14 -> flipOver(imageButtons, intArrayOf(8, 9, 10, 13, 14, 15, 18, 19, 20), false)
            R.id.imageButton15 -> flipOver(imageButtons, intArrayOf(9, 10, 14, 15, 19, 20), false)
            R.id.imageButton16 -> flipOver(imageButtons, intArrayOf(11, 12, 16, 17, 21, 22), false)
            R.id.imageButton17 -> flipOver(imageButtons, intArrayOf(11, 12, 13, 16, 17, 18, 21, 22, 23), false)
            R.id.imageButton18 -> flipOver(imageButtons, intArrayOf(12, 13, 14, 17, 18, 19, 22, 23, 24), false)
            R.id.imageButton19 -> flipOver(imageButtons, intArrayOf(13, 14, 15, 18, 19, 20, 23, 24, 25), false)
            R.id.imageButton20 -> flipOver(imageButtons, intArrayOf(14, 15, 19, 20, 24, 25), false)
            R.id.imageButton21 -> flipOver(imageButtons, intArrayOf(16, 17, 21, 22), false)
            R.id.imageButton22 -> flipOver(imageButtons, intArrayOf(16, 17, 18, 21, 22, 23), false)
            R.id.imageButton23 -> flipOver(imageButtons, intArrayOf(17, 18, 19, 22, 23, 24), false)
            R.id.imageButton24 -> flipOver(imageButtons, intArrayOf(18, 19, 20, 23, 24, 25), false)
            R.id.imageButton25 -> flipOver(imageButtons, intArrayOf(19, 20, 24, 25), false)
        }
        (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_sound)
    }
}