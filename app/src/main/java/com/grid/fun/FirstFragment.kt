package com.grid.`fun`

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.grid.`fun`.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Grabs bundle from either Third or Fourth Fragment
        val noLogo: Int? = arguments?.getInt("noLogo")

        /**
         * Checks if bundle is null, null bundle means it is the first time opening the app
         * So it will show the intro logo for 2 seconds and then make it invisible again
         * While the logo is showing, the buttons are disabled so they cannot be clicked.
         */
        if (noLogo == null) {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.logo.visibility = View.INVISIBLE
                binding.playButton.isEnabled = true
                binding.tutorial.isEnabled = true
                binding.muteUnmute.isEnabled = true
            }, 2000)
            binding.logo.visibility = View.VISIBLE
            binding.playButton.isEnabled = false
            binding.tutorial.isEnabled = false
            binding.muteUnmute.isEnabled = false
        }

        // Sets up audio, sets mute button click listener
        (activity as MainActivity).setUpAudio(requireContext(), binding.muteUnmute)

        // Sets the tutorial click listener to change to fragment four, also plays sound effect
        binding.tutorial.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FourthFragment)
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
        }

        // Sets the tutorial click listener to change to fragment two, also plays sound effect
        binding.playButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
        }
    }
}