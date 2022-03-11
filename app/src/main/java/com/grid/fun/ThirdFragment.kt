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
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        // Receive level value from second fragment and set text to appropriate level int
        var level: Int? = arguments?.getInt("level")

        // Sets trophy to invisible
        binding.trophy.visibility = View.INVISIBLE

        binding.mainMenu.setOnClickListener {
            // Sends user data to next fragment (third)
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }

        // Check what level user got to (if 11 they won because level 10 is the last level)
        // Set appropriate image resource for level gotten to. If user beat game,
        // Set bundle into navigator for trophy, and make trophy visibile, and make
        // cube invisible
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
                binding.trophy.visibility = View.VISIBLE
                binding.levelText.setImageDrawable(resources.getDrawable(R.drawable.level11, requireContext().theme))
                binding.gifImageView.visibility = View.INVISIBLE
                binding.mainMenu.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("beatGame", 1)
                    findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment, bundle)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}