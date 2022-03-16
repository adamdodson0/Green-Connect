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
import com.grid.`fun`.R
import com.grid.`fun`.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Grab bundle from third fragment to check if player beat the game
        var beatGame: Int? = arguments?.getInt("beatGame")

        binding.tutorial.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FourthFragment)
            (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
//            (activity as SecondFragment).playSoundEffect()
        }

        // Sets up audio, sets mute button click listener
        (activity as MainActivity).setUpAudio(requireContext(), binding.muteUnmute)

        // Check if user beat game or not
        if (beatGame == 1) {
            // Send user to next fragment with bundle for beating game
            val bundle = Bundle()
            bundle.putInt("beatGame", 1)
            binding.playButton.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
            }
        } else if (beatGame == null) {
            //binding.tutorial.setImageDrawable(resources.getDrawable(R.drawable.hearts_2, requireContext().theme))

            binding.logo.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                binding.logo.visibility = View.INVISIBLE
            }, 2000)

            binding.logo.visibility = View.VISIBLE

            // Send user to next fragment with no bundle for beating game
            binding.playButton.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
            }
        }
        else {
            // Send user to next fragment with no bundle for beating game
            binding.playButton.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                (activity as MainActivity).playSoundEffect(requireContext(), R.raw.click_button)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}