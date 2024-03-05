package com.example.gourmetsearcher.view

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.databinding.FragmentRestaurantDetailBinding
import com.example.gourmetsearcher.viewmodel.RestaurantDetailViewModel

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
            openWebBrowser(it)
        }
        viewModel.searchAddress.observe(viewLifecycleOwner) {
            openMap(it)
        }
        return binding.root
    }

    private fun openWebBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.setComponent(
            ComponentName(
                "com.android.chrome",
                "com.google.android.apps.chrome.Main"
            )
        )
        startActivity(intent)
    }

    private fun openMap(address: String) {
        val mapUrl = getString(R.string.map_url) + address
        val intent = Intent(Intent.ACTION_VIEW, mapUrl.toUri())
        intent.setComponent(
            ComponentName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )
        )
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}