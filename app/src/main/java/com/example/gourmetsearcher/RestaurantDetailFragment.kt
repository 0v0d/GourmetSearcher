package com.example.gourmetsearcher

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.gourmetsearcher.databinding.FragmentRestaurantDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailFragment : Fragment() {
    private var _binding: FragmentRestaurantDetailBinding? = null
    private val binding get() = _binding!!
    private val args: RestaurantDetailFragmentArgs by navArgs()
    private val viewModel: RestaurantDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        binding.restaurant = args.restaurantData

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.url.observe(viewLifecycleOwner) {
            startActivity(Intent(Intent.ACTION_VIEW, it.toUri()))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}