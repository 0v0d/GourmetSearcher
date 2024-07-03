package com.example.gourmetsearcher.ui.screen.seachlocation

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.model.data.CurrentLocation
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.state.LocationSearchState
import com.example.gourmetsearcher.utils.openSettings
import com.example.gourmetsearcher.viewmodel.SearchLocationViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchLocationScreen(
    onClick: () -> Unit,
    inputText: String,
    range: Int,
    modifier: Modifier = Modifier,
    viewModel: SearchLocationViewModel = hiltViewModel(),
    onNavigateToResultList: (SearchTerms) -> Unit,
) {
    val context = LocalContext.current
    val searchState by viewModel.locationSearchState.collectAsStateWithLifecycle()
    val locationData by viewModel.locationData.collectAsStateWithLifecycle()
    val locationPermissionsState =
        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)

    HandlePermissionEffects(
        locationPermissionsState = locationPermissionsState,
        onPermissionGranted = viewModel::handlePermissionGranted,
        onRationaleRequired = viewModel::handleRationaleRequired,
        onPermissionDenied = viewModel::handlePermissionDenied
    )
    HandleLocationDataEffect(locationData, inputText, range, onNavigateToResultList)

    SearchLocationContent(
        modifier = modifier,
        onClick = onClick,
        searchState = searchState,
        locationPermissionsState = locationPermissionsState,
        onRetry = viewModel::retryLocationRequest,
        onOpenSettings = {
            openSettings(context = context)
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun HandlePermissionEffects(
    locationPermissionsState: PermissionState,
    onPermissionGranted: () -> Unit,
    onRationaleRequired: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    LaunchedEffect(Unit) {
        locationPermissionsState.launchPermissionRequest()
    }

    LaunchedEffect(locationPermissionsState.status) {
        when {
            locationPermissionsState.status.isGranted -> onPermissionGranted()
            locationPermissionsState.status.shouldShowRationale -> onRationaleRequired()
            else -> onPermissionDenied()
        }
    }
}

@Composable
private fun HandleLocationDataEffect(
    locationData: CurrentLocation?,
    inputText: String,
    range: Int,
    onNavigateToResultList: (SearchTerms) -> Unit
) {
    LaunchedEffect(locationData) {
        locationData?.let { location ->
            onNavigateToResultList(SearchTerms(inputText, location, range))
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun SearchLocationContent(
    onClick: () -> Unit,
    searchState: LocationSearchState,
    locationPermissionsState: PermissionState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    onOpenSettings: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = { SearchLocationTopBar(onClick) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (searchState) {
                LocationSearchState.Loading -> LoadingContent()
                LocationSearchState.Error -> ErrorContent(
                    isGrated = locationPermissionsState.status.isGranted,
                    onRetry = {
                        if (locationPermissionsState.status.isGranted) {
                            onRetry()
                        } else {
                            locationPermissionsState.launchPermissionRequest()
                        }
                    },
                    onOpenSettings = onOpenSettings
                )

                LocationSearchState.RationalRequired -> RationaleContent(
                    onConfirmClick = { locationPermissionsState.launchPermissionRequest() }
                )
            }
        }
    }
}

@Composable
private fun SearchLocationTopBar(onClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(stringResource(id = R.string.search_location_top_bar_title)) },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
    )
}

@Composable
private fun LoadingContent() {
    CircularProgressIndicator()
}
