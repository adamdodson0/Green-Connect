package com.grid.`fun`

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grid.`fun`.databinding.FragmentFourthBinding

/**
 * This Fragment is the tutorial section to teach the user how to play the game. It is comprised
 * of imageButtons replicating the main game, imageViews teaching the user, and a 'next'
 * imageButton for the user to click to go on to the next part of the tutorial. The last click on
 * the 'next' button will send them back to FirstFragment (the main menu).
 *
 * @author Adam Dodson
 * @version 1.1.0
 * @since 23-03-2022
 */
class FourthFragment : Fragment() {

    // Variable holds binding
    private var _binding: FragmentFourthBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * This class sets the text on screen as well as setting all the on click listeners
     * for each image button.
     */
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create bundle to send to FirstFragment to stop logo from appearing
        val bundle = Bundle()
        // Will hold where user is at in tutorial
        var nextNum = 0
        // Holds the Integers for the imageButtons that have to be flipped
        val array = arrayOf(7, 8, 9, 12, 13, 14, 17, 18, 19)

        // Creates listOf all 25 of the imageButtons in the center of the screen that make up the game
        val imageButtons: List<ImageButton> = listOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
            binding.imageButton5, binding.imageButton6, binding.imageButton7, binding.imageButton8, binding.imageButton9,
            binding.imageButton10, binding.imageButton11, binding.imageButton12, binding.imageButton13,
            binding.imageButton14, binding.imageButton15, binding.imageButton16, binding.imageButton17,
            binding.imageButton18, binding.imageButton19, binding.imageButton20, binding.imageButton21,
            binding.imageButton22, binding.imageButton23, binding.imageButton24, binding.imageButton25)

        // Sets up the audio for the second fragment
        (activity as MainActivity).setUpAudio(requireContext(), binding.muteUnmute)

        /**
         * When user clicks glowingButton, perform flip on each button in array. Play sound effect,
         * then set visibilities and Drawable for user information.
         */
        binding.glowingButton.setOnClickListener {
            // Sets all imageButtons in array to Drawable square2
            array.forEach {
                imageButtons[it - 1].setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            } // Plays sound effect and sets visibilities for tutorial continuation
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_sound)
            binding.glowingButton.visibility = View.INVISIBLE
            binding.nextButton.visibility = View.VISIBLE
            binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.perfect, requireContext().theme))
        }

        /**
         * This next button is clicked continually by the user going through the tutorial,
         * each click will give nextNum++ to have a 'when' statement for correct actions
         * to occur. On the last click (5th click) it will send the user to FirstFragment as
         * the tutorial has been completed.
         */
        binding.nextButton.setOnClickListener {
            when (nextNum) {
                0 -> { // Sets all imageButtons in array Drawable to glowsquare
                    array.forEach {
                        imageButtons[it - 1].setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                    } // Sets Drawable for tutorial continuation
                    binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text3, requireContext().theme))
                }
                1 -> { // Sets every imageButton in imageButtons to be invisible
                    imageButtons.forEach {
                        it.visibility = View.INVISIBLE
                    } // Sets visibility and Drawable for tutorial continuation
                    binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text4, requireContext().theme))
                    binding.clicksLeft.visibility = View.VISIBLE
                }
                2 -> { // Sets visibility and Drawable for tutorial continuation
                    binding.clicksLeft.visibility = View.INVISIBLE
                    binding.hearts.visibility = View.VISIBLE
                    binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text5, requireContext().theme))
                }
                3 -> { // Sets visibility and Drawable for tutorial continuation
                    binding.hearts.visibility = View.INVISIBLE
                    binding.gifImageView.visibility = View.VISIBLE
                    binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text6, requireContext().theme))
                } // Takes user back to FirstFragment (main menu)
                4 -> findNavController().navigate(R.id.action_FourthFragment_to_FirstFragment, bundle)
            }
            // Adds 1 to nextNum and plays click sound effect
            nextNum++
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}