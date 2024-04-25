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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.databinding.FragmentSearchLocationBinding
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.state.LocationSearchState
import com.example.gourmetsearcher.viewmodel.SearchLocationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/** 位置情報検索画面 */
@AndroidEntryPoint
class SearchLocationFragment : Fragment() {
    private val viewModel: SearchLocationViewModel by viewModels()
    private var _binding: FragmentSearchLocationBinding? = null
    private val binding get() = _binding!!

    /** ナビゲーションの引数を取得するための変数 */
    private val args: SearchLocationFragmentArgs by navArgs()

    /** パーミッションのリクエスト結果を追跡するための変数 */
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val isGranted = it[Manifest.permission.ACCESS_COARSE_LOCATION] == true ||
                    it[Manifest.permission.ACCESS_FINE_LOCATION] == true
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    observeLocationData()
                }
                launch {
                    observeSearchState()
                }
                launch {
                    observerRetryEvent()
                }
                launch {
                    observerOpenLocationSettingEvent()
                }
            }
        }
        return binding.root
    }

    /**
     * パーミッションが許可されていない場合はリクエストする
     * 許可されている場合は位置情報の取得を開始する
     */
    private fun checkLocationPermission() {
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        } else {
            viewModel.getLocation()
        }
    }

    /**
     * どちらかの位置情報パーミッションが許可されているかを返す
     * @return 許可されている場合はtrue
     */
    private fun isLocationPermissionGranted(): Boolean {
        val isFineGranted = isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
        val isCoarseGranted = isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
        return isCoarseGranted || isFineGranted
    }

    /**
     * パーミッションが許可されているかを返す*
     * @param permission 許可を確認したいパーミッション
     * @return 許可されている場合はtrue
     */
    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /** 位置情報のパーミッションをリクエストする */
    private fun requestLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        locationPermissionRequest.launch(permissions)
    }

    /** パーミッションが許可されなかった場合の処理 */
    private fun handleLocationPermissionCancel() {
        if (shouldShowLocationPermissionRationale()) {
            showPermissionExplanationDialog()
        } else {
            showError()
        }
    }

    /**
     * パーミッションの説明ダイアログを表示するかどうかを返す
     * @return パーミッションの説明ダイアログを表示する場合はtrue
     */
    private fun shouldShowLocationPermissionRationale(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    /** エラーを表示する */
    private fun showError() {
        binding.loadingProgressBar.isVisible = false
        binding.retryButton.isVisible = true
        binding.redirectSettingButton.isVisible = true
        binding.locationErrorTextView.isVisible = true
        binding.locationErrorTextView.text = errorText()
    }

    /**
     * 許可内容に応じたエラーメッセージを返す
     * @return エラーメッセージ
     */
    private fun errorText(): String {
        return getString(
            if (isLocationPermissionGranted()) {
                R.string.location_error_message
            } else {
                R.string.location_permission_denied_message
            }
        )
    }

    /**  パーミッションの説明ダイアログを表示する */
    private fun showPermissionExplanationDialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.permissionExplanationDialog)
            .setTitle(R.string.location_permission_required_title)
            .setMessage(R.string.location_permission_required_message)
            .setPositiveButton(R.string.ok_button) { _, _ -> requestLocationPermission() }
            .create()
            .show()
    }

    /** 位置情報の設定画面を開くイベントを監視 */
    private suspend fun observerOpenLocationSettingEvent() {
        viewModel.openLocationSettingEvent.collect {
            openLocationSetting()
        }
    }

    /** リトライイベントを監視 */
    private suspend fun observerRetryEvent() {
        viewModel.retryEvent.collect {
            showLoading()
            checkLocationPermission()
        }
    }

    /** 位置情報の取得状態を監視 */
    private suspend fun observeSearchState() {
        //位置情報の取得状態を監視
        viewModel.searchState.collect { state ->
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
    }


    /** 位置情報の取得状態を監視 */
    private suspend fun observeLocationData() {
        viewModel.locationData.collect { locationData ->
            if (locationData != null) {
                navigateToResultListFragment(SearchTerms(args.inputText, locationData, args.range))
            }
        }
    }

    /**
     * 結果一覧画面を開く
     * @param searchTerms 検索条件
     */
    private fun navigateToResultListFragment(searchTerms: SearchTerms) {
        val action = SearchLocationFragmentDirections.actionToResultListFragment(searchTerms)
        findNavController().navigate(action)
    }

    /** 新たなタスクで位置情報の設定画面を開く */
    private fun openLocationSetting() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    /** 読み込み中のUIを表示する */
    private fun showLoading() {
        binding.loadingProgressBar.isVisible = true
        binding.redirectSettingButton.isVisible = false
        binding.retryButton.isVisible = false
        binding.locationErrorTextView.isVisible = false
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}