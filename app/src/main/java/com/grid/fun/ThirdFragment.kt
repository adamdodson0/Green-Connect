package com.grid.`fun`

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.grid.`fun`.databinding.FragmentThirdBinding

/**
 * This Fragment is where the user goes when winning or losing the game in the
 * SecondFragment. Depending on what level the user got to different drawables
 * will be displayed to the user. Although there will be only one imageButton
 * that will take the user back to the FirstFragment.
 *
 * @author Adam Dodson
 * @version 1.1.0
 * @since 23-03-2022
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Receive level value from second fragments bundle
        val level: Int? = arguments?.getInt("level")

        // Create bundle to send to FirstFragment to stop logo from appearing
        val bundle = Bundle()

        // Sets main menu button to send user to FirstFragment when clicked (with
        // bundle data) Also plays sound effect.
        binding.mainMenu.setOnClickListener {
            // Sends user data to next fragment (third)
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment, bundle)
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
        }

        // Sets up audio, sets the mute button's clickListener
        (activity as MainActivity).setUpAudio(requireContext(), binding.muteUnmute)

        /**
         * Checks what level the user got to. If they got to 1-10 (inclusive) then it just
         * sets which imageDrawable they will get. If they got to 11 (they beat all the levels)
         * then it will save to sharedPreference (whats used to make trophy appear in SecondFragment).
         * A trophy will also appear and the gif will be invisible.
         */
        when (level) {
            1 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level1, requireContext().theme))
            2 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level2, requireContext().theme))
            3 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level3, requireContext().theme))
            4 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level4, requireContext().theme))
            5 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level5, requireContext().theme))
            6 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level6, requireContext().theme))
            7 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level7, requireContext().theme))
            8 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level8, requireContext().theme))
            9 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level9, requireContext().theme))
            10 -> binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level10, requireContext().theme))
            11 -> { // Display text for beating game. Beating game sends bundle to next
                // fragment to show trophy for user next time game is played
                // Sets trophy to visible
                if (SavePreference(requireContext()).getInteger("beatGame") != 1) {
                    SavePreference(requireContext()).saveInt("beatGame", 1)
                }
                binding.trophy.visibility = View.VISIBLE
                binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level11, requireContext().theme))
                binding.gifImageView.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}