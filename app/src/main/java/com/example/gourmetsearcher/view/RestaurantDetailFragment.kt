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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.databinding.FragmentRestaurantDetailBinding
import com.example.gourmetsearcher.viewmodel.RestaurantDetailViewModel
import kotlinx.coroutines.launch

/** レストラン詳細画面 */
class RestaurantDetailFragment : Fragment() {
    private val viewModel: RestaurantDetailViewModel by viewModels()
    private var _binding: FragmentRestaurantDetailBinding? = null
    private val binding get() = _binding!!
    /** argsによって渡されたレストラン情報を取得 */
    private val args: RestaurantDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        binding.restaurant = args.restaurantData

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    observeUrl()
                }
                launch {
                    observeAddress()
                }
            }
        }
        return binding.root
    }

    /** searchAddressの変更を監視 */
    private suspend fun observeAddress() {
        viewModel.searchAddress.collect {
            if (it != null) {
                openMap(it)
            }
        }
    }

    /** urlの変更を監視 */
    private suspend fun observeUrl() {
        viewModel.url.collect {
            if (it != null) {
                openWebBrowser(it)
            }
        }
    }

    /**
     * WebViewでURLを開く
     * @param url 開くURL
     */
    private fun openWebBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.setComponent(
            ComponentName(
                "com.android.chrome",
                "com.google.android.apps.chrome.Main"
            )
        )
        startActivity(intent)
        viewModel.clearUrl()
    }

    /**
     * GoogleMapで住所を開く
     * @param address 開く住所
     */
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
        viewModel.clearAddress()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}