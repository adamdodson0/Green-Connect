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
import com.grid.`fun`.databinding.FragmentFourthBinding

/**
 *
 */
class FourthFragment : Fragment() {

    // Variable holds binding
    private var _binding: FragmentFourthBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    lateinit var imageButtons: List<ImageButton>

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

        // Sends user data to next fragment (third)
        val bundle = Bundle()

        var nextNum: Int = 0

        val array = arrayOf(7, 8, 9, 12, 13, 14, 17, 18, 19)

        // Creates listOf all 25 of the imageButtons in the center of the screen that make up the game
        imageButtons = listOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
            binding.imageButton5, binding.imageButton6, binding.imageButton7, binding.imageButton8, binding.imageButton9,
            binding.imageButton10, binding.imageButton11, binding.imageButton12, binding.imageButton13,
            binding.imageButton14, binding.imageButton15, binding.imageButton16, binding.imageButton17,
            binding.imageButton18, binding.imageButton19, binding.imageButton20, binding.imageButton21,
            binding.imageButton22, binding.imageButton23, binding.imageButton24, binding.imageButton25)

        // Sets up the first levels image buttons, clicksLeft, and levelText
        //startNextLevel(bundle)

        // Sets up the audio for the second fragment
        (activity as MainActivity).setUpAudio(requireContext(), binding.muteUnmute)

        // When user clicks glowing button, perform flip on buttons
        binding.glowingButton.setOnClickListener {

            array.forEach() {
                imageButtons[it - 1].setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            }
            binding.glowingButton.visibility = View.INVISIBLE
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_sound)

            Handler(Looper.getMainLooper()).postDelayed({
                binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.perfect, requireContext().theme))
                binding.nextButton.visibility = View.VISIBLE
            }, 500)
        }

        binding.nextButton.setOnClickListener {
            if (nextNum == 0) {
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)

                array.forEach() {
                    imageButtons[it - 1].setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                }
                binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text3, requireContext().theme))
                nextNum++
            } else if (nextNum == 1) {
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
                nextNum++
                binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text4, requireContext().theme))

                imageButtons.forEach() {
                    it.visibility = View.INVISIBLE
                }
                binding.clicksLeft.visibility = View.VISIBLE
            }
            else if (nextNum == 2) {
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
                nextNum++
                binding.clicksLeft.visibility = View.INVISIBLE
                binding.hearts.visibility = View.VISIBLE

                binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text5, requireContext().theme))
            }
            else if (nextNum == 3) {
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
                nextNum++
                binding.hearts.visibility = View.INVISIBLE
                binding.gifImageView.visibility = View.VISIBLE

                binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text6, requireContext().theme))
            }
            else if (nextNum == 4) {
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
                nextNum++

                findNavController().navigate(R.id.action_FourthFragment_to_FirstFragment, bundle)
                //binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text7, requireContext().theme))
            }
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
        }
        else {
            imageButton.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}