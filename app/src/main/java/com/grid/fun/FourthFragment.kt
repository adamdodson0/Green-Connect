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

        // Sets up the first levels image buttons, clicksLeft, and levelText
        //startNextLevel(bundle)

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

        // When user clicks glowing button, perform flip on buttons
        binding.glowingButton.setOnClickListener {
            binding.imageButton7.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            binding.imageButton8.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            binding.imageButton9.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            binding.imageButton12.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            binding.imageButton14.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            binding.imageButton17.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            binding.imageButton18.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
            binding.imageButton19.setImageDrawable(resources.getDrawable(R.drawable.square2, requireContext().theme))
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
                binding.imageButton7.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.imageButton8.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.imageButton9.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.imageButton12.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.imageButton13.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.imageButton14.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.imageButton17.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.imageButton18.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.imageButton19.setImageDrawable(resources.getDrawable(R.drawable.glowsquare, requireContext().theme))
                binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text3, requireContext().theme))
                nextNum++
            } else if (nextNum == 1) {
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
                nextNum++
                binding.textTutorial.setImageDrawable(resources.getDrawable(R.drawable.tutorial_text4, requireContext().theme))
                binding.imageButton1.visibility = View.INVISIBLE
                binding.imageButton2.visibility = View.INVISIBLE
                binding.imageButton3.visibility = View.INVISIBLE
                binding.imageButton4.visibility = View.INVISIBLE
                binding.imageButton5.visibility = View.INVISIBLE
                binding.imageButton6.visibility = View.INVISIBLE
                binding.imageButton7.visibility = View.INVISIBLE
                binding.imageButton8.visibility = View.INVISIBLE
                binding.imageButton9.visibility = View.INVISIBLE
                binding.imageButton10.visibility = View.INVISIBLE
                binding.imageButton11.visibility = View.INVISIBLE
                binding.imageButton12.visibility = View.INVISIBLE
                binding.imageButton13.visibility = View.INVISIBLE
                binding.imageButton14.visibility = View.INVISIBLE
                binding.imageButton15.visibility = View.INVISIBLE
                binding.imageButton16.visibility = View.INVISIBLE
                binding.imageButton17.visibility = View.INVISIBLE
                binding.imageButton18.visibility = View.INVISIBLE
                binding.imageButton19.visibility = View.INVISIBLE
                binding.imageButton20.visibility = View.INVISIBLE
                binding.imageButton21.visibility = View.INVISIBLE
                binding.imageButton22.visibility = View.INVISIBLE
                binding.imageButton23.visibility = View.INVISIBLE
                binding.imageButton24.visibility = View.INVISIBLE
                binding.imageButton25.visibility = View.INVISIBLE
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