package com.grid.`fun`

import android.os.Bundle
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Grab bundle from third fragment to check if player beat the game
        var beatGame: Int? = arguments?.getInt("beatGame")

        binding.tutorial.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FourthFragment)
        }

        // Check if user beat game or not
        if (beatGame == 1) {
            // Send user to next fragment with bundle for beating game
            val bundle = Bundle()
            bundle.putInt("beatGame", 1)
            binding.playButton.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        } else {
            // Send user to next fragment with no bundle for beating game
            binding.playButton.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}