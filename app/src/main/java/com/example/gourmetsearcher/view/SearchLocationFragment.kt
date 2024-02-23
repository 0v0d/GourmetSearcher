package com.example.gourmetsearcher.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.example.gourmetsearcher.model.SearchTerms
import com.example.gourmetsearcher.databinding.FragmentSearchLocationBinding
import com.example.gourmetsearcher.viewmodel.SearchLocationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SearchLocationFragment : Fragment() {
    private val viewModel: SearchLocationViewModel by viewModels()
    private var _binding: FragmentSearchLocationBinding? = null
    private val binding get() = _binding!!
    private val args: SearchLocationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchLocationBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        checkLocationPermission(requireContext())
        observeViewModel()
        return binding.root
    }

    private fun checkLocationPermission(context: Context) {
        if (!isGranted()) {
            // パーミッションがない場合は、リクエスト
            requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            // パーミッションがある場合は、位置情報取得
            viewModel.getLocation(context)
        }
    }

    private fun isGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // パーミッションリクエストの結果を受け取る
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { hasPermission ->
            if (hasPermission) {
                // リクエスト許可時の処理
                viewModel.getLocation(requireContext())
            } else {
                // リクエスト拒否時の処理
                handleLocationPermissionCancel()
            }
        }

    private fun handleLocationPermissionCancel() {
        val isShowPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (isShowPermissionRationale) {
            showPermissionExplanationDialog(requireContext())
        } else {
            showError()
        }
    }

    // パーミッションの説明ダイアログを表示
    private fun showPermissionExplanationDialog(context: Context) {
        MaterialAlertDialogBuilder(context, R.style.PermissionExplanationDialog)
            .setTitle(R.string.location_permission_required_title)
            .setMessage(R.string.location_permission_required_message)
            .setPositiveButton(R.string.ok_button) { _, _ ->
                requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            .create()
            .show()
    }

    private fun observeViewModel() {
        // ViewModelのlocationDataを監視し、UIを更新
        viewModel.locationData.observe(viewLifecycleOwner) { locationData ->
            // UIの更新処理
            val searchTerms = SearchTerms(args.inputText, locationData, args.range)
            navigateToResultListFragment(searchTerms)
        }

        // ViewModelのerrorStateを監視し、エラーがあればUIを更新
        viewModel.errorState.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                showError()
            }
        }
    }

    // 検索結果一覧画面に遷移
    private fun navigateToResultListFragment(searchTerms: SearchTerms) {
        val action = SearchLocationFragmentDirections.actionToResultListFragment(searchTerms)
        findNavController().navigate(action)
    }

    private fun showError() {
        binding.loadingProgressBar.isVisible = false
        binding.retryButton.isVisible = isGranted()
        binding.errorText.isVisible = true
        if (isGranted()) {
            binding.errorText.text = getString(R.string.location_error_message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}