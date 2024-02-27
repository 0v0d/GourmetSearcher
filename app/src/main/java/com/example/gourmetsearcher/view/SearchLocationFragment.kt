package com.example.gourmetsearcher.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.databinding.FragmentSearchLocationBinding
import com.example.gourmetsearcher.model.SearchTerms
import com.example.gourmetsearcher.viewmodel.LocationSearchState
import com.example.gourmetsearcher.viewmodel.SearchLocationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchLocationFragment : Fragment() {
    private val viewModel: SearchLocationViewModel by viewModels()
    private var _binding: FragmentSearchLocationBinding? = null
    private val binding get() = _binding!!
    private val args: SearchLocationFragmentArgs by navArgs()

    // パーミッションのリクエスト結果を追跡するための変数
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { hasPermission ->
            // パーミッションの結果に対する処理
            val isGranted =
                hasPermission[Manifest.permission.ACCESS_COARSE_LOCATION] == true ||
                        hasPermission[Manifest.permission.ACCESS_FINE_LOCATION] == true
            if (isGranted) {
                viewModel.getLocation()
            } else {
                handleLocationPermissionCancel()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchLocationBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        checkLocationPermission()
        observeViewModel()
        return binding.root
    }

    private fun checkLocationPermission() {
        // 位置情報のパーミッションが許可されていない場合はリクエストする
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        } else {
            // 位置情報の取得を開始
            viewModel.getLocation()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        val isFineGranted = isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
        val isCoarseGranted = isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
        // どちらかのパーミッションが許可されていればtrueを返す
        return isCoarseGranted || isFineGranted
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        locationPermissionRequest.launch(permissions)
    }

    private fun handleLocationPermissionCancel() {
        // パーミッションが許可されなかった場合の処理
        if (shouldShowLocationPermissionRationale()) {
            // パーミッションの説明ダイアログを表示
            showPermissionExplanationDialog()
        } else {
            showError()
        }
    }

    // パーミッションの説明ダイアログを表示するかどうかを返す
    private fun shouldShowLocationPermissionRationale(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private fun showError() {
        binding.apply {
            loadingProgressBar.isVisible = false
            errorButtonLayout.isVisible = true
            locationErrorTextView.isVisible = true
            locationErrorTextView.text =errorText()
        }
    }

    private fun errorText(): String {
       return getString(
            if (isLocationPermissionGranted()) {
                R.string.location_error_message
            } else {
                R.string.location_permission_denied_message
            }
        )
    }

    private fun showPermissionExplanationDialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.PermissionExplanationDialog)
            .setTitle(R.string.location_permission_required_title)
            .setMessage(R.string.location_permission_required_message)
            .setPositiveButton(R.string.ok_button) { _, _ -> requestLocationPermission() }
            .create()
            .show()
    }

    private fun observeViewModel() {
        // 位置情報の取得結果を監視
        viewModel.locationData.observe(viewLifecycleOwner) { locationData ->
            navigateToResultListFragment(SearchTerms(args.inputText, locationData, args.range))
        }

        //位置情報の取得状態を監視
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LocationSearchState.LOADING -> {
                    showLoading()
                }
                // 位置情報の取得に失敗した場合の処理
                else -> {
                    showError()
                }
            }
        }


        // 位置情報の設定画面を開くイベントを監視
        viewModel.openLocationSettingEvent.observe(viewLifecycleOwner) {
            openLocationSetting()
        }

        viewModel.retryEvent.observe(viewLifecycleOwner) {
            showLoading()
            checkLocationPermission()
        }
    }

    private fun navigateToResultListFragment(searchTerms: SearchTerms) {
        val action = SearchLocationFragmentDirections.actionToResultListFragment(searchTerms)
        findNavController().navigate(action)
    }

    private fun openLocationSetting() {
        //新たなタスクで位置情報の設定画面を開く
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    private fun showLoading() {
        binding.loadingProgressBar.isVisible = true
        binding.errorButtonLayout.isVisible = false
        binding.locationErrorTextView.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}