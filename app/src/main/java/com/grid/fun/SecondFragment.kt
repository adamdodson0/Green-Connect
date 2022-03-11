package com.grid.`fun`

import android.annotation.SuppressLint
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
class SecondFragment : Fragment() {

    // Variable holds the amount of clicks left in puzzle
    var clicksLeft: Int = 0
    // Variable holds the level number
    var level: Int = 0

    var heartsNum: Int = 3

    // Variable holds binding
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

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
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sends user data to next fragment (third)
        val bundle = Bundle()

        // Grabs bundle from first fragment to check if player has beaten the game
        var beatGame: Int? = arguments?.getInt("beatGame")
        // Set trophy to visible to show user beat the game before
        if (beatGame == 1) {
            binding.trophy.visibility = View.VISIBLE
        }

        // Sets up the first levels image buttons, clicksLeft, and levelText
        startNextLevel(bundle, false)

        // Submit button for user, checks if buttons all have same drawable resource, if they do, continue to
        // next level on same fragment. If not, send to next fragment.
        binding.submitButton.setOnClickListener {
            if (binding.imageButton1.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton2.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton3.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton4.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton5.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton6.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton7.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton8.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton9.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton10.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton11.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton12.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton13.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton14.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton15.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton16.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton17.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton18.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton19.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton20.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton21.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton22.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton23.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton24.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState &&
                binding.imageButton25.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState) {
                // Next level is initiated (in same fragment)
                startNextLevel(bundle, false)
            } else { // User lost the game, sent to next fragment (third)
                heartsNum -= 1
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

        /**
         * Gives each imageButton click effect, passes a number to show position of button
         * When button is clicked, the button and the buttons around (effect within 1 button radius)
         * it all change image.
         */
        binding.imageButton1.setOnClickListener {
            flipAll(intArrayOf(1, 2, 6, 7), false)
        }
        binding.imageButton2.setOnClickListener {
            flipAll(intArrayOf(1, 2, 3, 6, 7, 8), false)
        }
        binding.imageButton3.setOnClickListener {
            flipAll(intArrayOf(2, 3, 4, 7, 8, 9), false)
        }
        binding.imageButton4.setOnClickListener {
            flipAll(intArrayOf(3, 4, 5, 8, 9, 10), false)
        }
        binding.imageButton5.setOnClickListener {
            flipAll(intArrayOf(4, 5, 9, 10), false)
        }
        binding.imageButton6.setOnClickListener {
            flipAll(intArrayOf(1, 2, 6, 7, 11, 12), false)
        }
        binding.imageButton7.setOnClickListener {
            flipAll(intArrayOf(1, 2, 3, 6, 7, 8, 11, 12, 13), false)
        }
        binding.imageButton8.setOnClickListener {
            flipAll(intArrayOf(2, 3, 4, 7, 8, 9, 12, 13, 14), false)
        }
        binding.imageButton9.setOnClickListener {
            flipAll(intArrayOf(3, 4, 5, 8, 9, 10, 13, 14, 15), false)
        }
        binding.imageButton10.setOnClickListener {
            flipAll(intArrayOf(4, 5, 9, 10, 14, 15), false)
        }
        binding.imageButton11.setOnClickListener {
            flipAll(intArrayOf(6, 7, 11, 12, 16, 17), false)
        }
        binding.imageButton12.setOnClickListener {
            flipAll(intArrayOf(6, 7, 8, 11, 12, 13, 16, 17, 18), false)
        }
        binding.imageButton13.setOnClickListener {
            flipAll(intArrayOf(7, 8, 9, 12, 13, 14, 17, 18, 19), false)
        }
        binding.imageButton14.setOnClickListener {
            flipAll(intArrayOf(8, 9, 10, 13, 14, 15, 18, 19, 20), false)
        }
        binding.imageButton15.setOnClickListener {
            flipAll(intArrayOf(9, 10, 14, 15, 19, 20), false)
        }
        binding.imageButton16.setOnClickListener {
            flipAll(intArrayOf(11, 12, 16, 17, 21, 22), false)
        }
        binding.imageButton17.setOnClickListener {
            flipAll(intArrayOf(11, 12, 13, 16, 17, 18, 21, 22, 23), false)
        }
        binding.imageButton18.setOnClickListener {
            flipAll(intArrayOf(12, 13, 14, 17, 18, 19, 22, 23, 24), false)
        }
        binding.imageButton19.setOnClickListener {
            flipAll(intArrayOf(13, 14, 15, 18, 19, 20, 23, 24, 25), false)
        }
        binding.imageButton20.setOnClickListener {
            flipAll(intArrayOf(14, 15, 19, 20, 24, 25), false)
        }
        binding.imageButton21.setOnClickListener {
            flipAll(intArrayOf(16, 17, 21, 22), false)
        }
        binding.imageButton22.setOnClickListener {
            flipAll(intArrayOf(16, 17, 18, 21, 22, 23), false)
        }
        binding.imageButton23.setOnClickListener {
            flipAll(intArrayOf(17, 18, 19, 22, 23, 24), false)
        }
        binding.imageButton24.setOnClickListener {
            flipAll(intArrayOf(18, 19, 20, 23, 24, 25), false)
        }
        binding.imageButton25.setOnClickListener {
            flipAll(intArrayOf(19, 20, 24, 25), false)
        }
    }

    /**
     * This function checks the imageButtons drawable image and changes it to the opposite
     * image for the puzzle board.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun flipOver(imageButton: ImageButton) {
        // Check if Drawable is square1 or square2, and changing it to the opposite
            if (imageButton.drawable.constantState == resources.getDrawable(R.drawable.square2, requireContext().theme).constantState) {
                imageButton.setImageDrawable(resources.getDrawable(R.drawable.square1, requireContext().theme))
            } else {
                imageButton.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            }
    }

    /**
     * This function checks the int passed into the function and executes the correct
     * call to flip the correct imageButton
     */
    private fun flipAll(array: IntArray, settingUp: Boolean) {
        for (num in array) {
            when (num) {
                1 -> flipOver(binding.imageButton1)
                2 -> flipOver(binding.imageButton2)
                3 -> flipOver(binding.imageButton3)
                4 -> flipOver(binding.imageButton4)
                5 -> flipOver(binding.imageButton5)
                6 -> flipOver(binding.imageButton6)
                7 -> flipOver(binding.imageButton7)
                8 -> flipOver(binding.imageButton8)
                9 -> flipOver(binding.imageButton9)
                10 -> flipOver(binding.imageButton10)
                11 -> flipOver(binding.imageButton11)
                12 -> flipOver(binding.imageButton12)
                13 -> flipOver(binding.imageButton13)
                14 -> flipOver(binding.imageButton14)
                15 -> flipOver(binding.imageButton15)
                16 -> flipOver(binding.imageButton16)
                17 -> flipOver(binding.imageButton17)
                18 -> flipOver(binding.imageButton18)
                19 -> flipOver(binding.imageButton19)
                20 -> flipOver(binding.imageButton20)
                21 -> flipOver(binding.imageButton21)
                22 -> flipOver(binding.imageButton22)
                23 -> flipOver(binding.imageButton23)
                24 -> flipOver(binding.imageButton24)
                25 -> flipOver(binding.imageButton25)
                else -> { } // Do nothing
            }
        }
        // sets the clicksLeft variable lower 1 and displays it,
        //  if it falls to zero, it wait 1 second and perform the click to check results
        if (!settingUp) {
            clicksLeft -= 1
            if (clicksLeft >= 0)
                binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
            if (clicksLeft == 0) {
                binding.submitButton.visibility = View.INVISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.submitButton.visibility = View.VISIBLE
                    binding.submitButton.performClick()
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
            1 -> levelOne()
            2 -> levelTwo()
            3 -> levelThree()
            4 -> levelFour()
            5 -> levelFive()
            6 -> levelSix()
            7 -> levelSeven()
            8 -> levelEight()
            9 -> levelNine()
            10 -> levelTen()
            else -> {
                bundle.putInt("level", level)
                findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
            }
        }

    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level one
     * The level is randomly chosen from three different potential levels.
     */
    private fun resetGrid() {
        binding.imageButton1.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton2.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton3.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton4.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton5.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton6.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton7.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton8.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton9.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton10.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton11.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton12.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton13.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton14.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton15.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton16.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton17.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton18.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton19.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton20.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton21.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton22.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton23.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton24.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        binding.imageButton25.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level one
     * The level is randomly chosen from three different potential levels.
     */
    private fun levelOne() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(2, 3, 4, 6, 8, 10, 11, 12, 14, 15, 16, 18, 20, 22, 23, 24), true)
            2 -> flipAll(intArrayOf(1, 2, 4, 5, 6, 7, 9, 10, 16, 17, 19, 20, 21, 22, 24, 25), true)
            3 -> flipAll(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 24, 25), true)
            4 -> flipAll(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8,  9, 10, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25), true)
            5 -> flipAll(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25), true)
        }
        clicksLeft = 4
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level two
     */
    private fun levelTwo() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(1,2,4, 5, 6, 8, 10, 12, 13, 14, 22, 23, 24), true)
            2 -> flipAll(intArrayOf(2, 3, 4, 7, 8, 9, 11, 13, 15, 16, 18, 20, 21, 23, 25), true)
            3 -> flipAll(intArrayOf(1, 2, 4, 5, 6, 7, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25), true)
            4 -> flipAll(intArrayOf(1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 23, 24, 25), true)
            5 -> flipAll(intArrayOf(11, 12, 13, 14, 15), true)
        }
        clicksLeft = 4
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level three
     */
    private fun levelThree() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(3, 8, 11, 12, 14, 15, 18, 23), true)
            2 -> flipAll(intArrayOf(3, 4, 5, 7, 10, 11, 13, 15, 16, 20, 21, 22, 23, 24, 25), true)
            3 -> flipAll(intArrayOf(4, 5, 6, 7, 11, 12, 14, 15, 19, 20, 21, 22), true)
            4 -> flipAll(intArrayOf(1, 2, 8, 9, 10, 11, 12, 13, 14, 15, 18, 19, 20, 21, 22), true)
            5 -> flipAll(intArrayOf(1, 2, 4, 5, 6, 7, 9, 10, 13), true)
        }
        clicksLeft = 4
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level four
     */
    private fun levelFour() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(6, 8, 10, 11, 13, 15, 16, 17, 19, 20, 22, 23, 24), true)
            2 -> flipAll(intArrayOf(3, 4, 5, 6, 7, 8, 11, 12, 14, 15, 19, 20, 21, 22), true)
            3 -> flipAll(intArrayOf(1, 2, 3, 6, 9, 12, 15, 16, 18, 20, 21, 22, 24, 25), true)
            4 -> flipAll(intArrayOf(1, 2, 3, 18, 19, 20, 21, 22, 24, 25), true)
            5 -> flipAll(intArrayOf(6, 7, 9, 10, 11, 12, 13, 16, 17, 19, 20), true)
        }
        clicksLeft = 4
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level five
     */
    private fun levelFive() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 18, 21, 22, 23), true)
            2 -> flipAll(intArrayOf(9,10, 11, 12, 13, 14, 15, 18, 23, 24, 25), true)
            3 -> flipAll(intArrayOf(2, 3, 5, 7, 9, 13, 18, 24, 25), true)
            4 -> flipAll(intArrayOf(2, 3, 4, 9, 10, 12, 13, 15, 19, 20, 22, 23, 24), true)
            5 -> flipAll(intArrayOf(1, 3, 5, 6, 8, 10, 12, 13, 15, 17, 18, 19, 22, 23, 24), true)
        }
        clicksLeft = 4
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level six
     */
    private fun levelSix() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(1, 2, 3, 6, 7, 8, 9, 10, 13, 14, 15, 24, 25), true)
            2 -> flipAll(intArrayOf(1, 2, 3, 6, 7, 9, 10, 13, 14, 15, 23, 24, 25), true)
            3 -> flipAll(intArrayOf(1, 4, 7, 8, 10, 11, 15, 16, 20, 22, 23, 24), true)
            4 -> flipAll(intArrayOf(4, 5, 9, 10, 11, 12, 13, 17, 18, 20, 22, 23, 25), true)
            5 -> flipAll(intArrayOf(1, 3, 4, 6, 8, 10, 11, 13, 15, 16, 18, 20, 21, 23, 24), true)
        }
        clicksLeft = 5
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level seven
     */
    private fun levelSeven() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(3, 4, 5, 6, 7, 8, 11, 12, 13, 16, 17, 18, 19, 20, 23), true)
            2 -> flipAll(intArrayOf(1, 2, 4, 5, 6, 7, 11, 12, 18, 19, 20, 23), true)
            3 -> flipAll(intArrayOf(1, 2, 7, 10, 12, 15, 16, 18, 19, 23, 24, 25), true)
            4 -> flipAll(intArrayOf(2, 5, 7, 10, 12, 13, 15, 16, 17, 21, 22), true)
            5 -> flipAll(intArrayOf(1, 4, 6, 9, 11, 12, 14, 15, 16, 17, 21, 22), true)
        }
        clicksLeft = 5
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level eight
     */
    private fun levelEight() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(1, 3, 5, 6, 9, 11, 12, 13, 16, 17, 18, 19, 20, 21, 22), true)
            2 -> flipAll(intArrayOf(1, 2, 4, 5, 6, 8, 9, 11, 15, 17, 18, 19, 24, 25), true)
            3 -> flipAll(intArrayOf(2, 4, 7, 9, 13, 14, 15, 16, 18, 19, 21, 23, 24), true)
            4 -> flipAll(intArrayOf(2, 4, 6, 10, 12, 14, 24, 25), true)
            5 -> flipAll(intArrayOf(3, 6, 7, 16, 17, 23), true)
        }
        clicksLeft = 5
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level nine
     */
    private fun levelNine() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(4, 5, 7, 10, 12, 15, 17, 18, 20, 23, 24, 25), true)
            2 -> flipAll(intArrayOf(2, 4, 7, 9, 11, 15, 16, 19, 21, 24), true)
            3 -> flipAll(intArrayOf(7, 8, 10, 14, 15, 16, 17, 21, 23, 25), true)
            4 -> flipAll(intArrayOf(2, 5, 6, 9, 12, 13, 15, 19, 20, 21, 22), true)
            5 -> flipAll(intArrayOf(8, 11, 12, 13, 14, 15, 19, 20, 23, 24, 25), true)
        }
        clicksLeft = 5
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    /**
     * This function sets the image buttons backgrounds, clicks left, and text for level ten
     */
    private fun levelTen() {
        when ((1..5).random()) {
            1 -> flipAll(intArrayOf(1, 2, 6, 7, 13, 16, 17, 21, 22), true)
            2 -> flipAll(intArrayOf(1, 2, 9, 10, 11, 12, 13, 19, 20, 21, 22), true)
            3 -> flipAll(intArrayOf(1, 5, 7, 8, 9, 16, 20, 22, 23, 24), true)
            4 -> flipAll(intArrayOf(1, 2, 6, 8, 10, 12, 14, 17, 18, 19, 24, 25), true)
            5 -> flipAll(intArrayOf(1, 3, 4, 8, 14, 15, 17, 18, 20, 21, 22, 23, 24, 25), true)
        }
        clicksLeft = 6
        binding.clicksLeft.text = getString(R.string.clicks_left, clicksLeft)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}