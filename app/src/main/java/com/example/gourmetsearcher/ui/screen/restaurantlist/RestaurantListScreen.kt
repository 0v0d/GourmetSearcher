package com.example.gourmetsearcher.ui.screen.restaurantlist

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.model.data.SearchTerms
import com.example.gourmetsearcher.model.domain.ShopsDomain
import com.example.gourmetsearcher.state.SearchState
import com.example.gourmetsearcher.viewmodel.RestaurantListViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RestaurantListScreen(
    onClick: () -> Unit,
    searchTerms: SearchTerms,
    modifier: Modifier = Modifier,
    viewModel: RestaurantListViewModel = hiltViewModel(),
    onNavigateToDetail: (ShopsDomain) -> Unit
) {
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    val shops by viewModel.shops.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.searchRestaurants(searchTerms)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            RestaurantDetailTopAppBar(onClick = onClick)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            when (searchState) {
                SearchState.Loading -> Loading()
                SearchState.Success -> shops?.let {
                    RestaurantList(
                        it,
                        onNavigateToDetail
                    )
                }

                SearchState.EmptyResult -> ErrorContent(
                    R.string.restaurant_list_empty_result_message
                )

                SearchState.NetworkError -> ErrorContent(
                    R.string.restaurant_list_network_error_message,
                    onRetry = { viewModel.searchRestaurants(searchTerms) }
                )
            }
        }
    }
}

@Composable
fun RestaurantList(
    shops: ImmutableList<ShopsDomain>,
    navigateToDetail: (ShopsDomain) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(shops) { restaurant ->
            RestaurantItem(
                restaurant = restaurant,
                onClick = { navigateToDetail(restaurant) }
            )
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun RestaurantItem(
    restaurant: ShopsDomain,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(restaurant.photo.pc.l)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(85.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                RestaurantInfo(restaurant = restaurant)
            }
        }
    }
}

@Composable
fun RestaurantInfo(
    restaurant: ShopsDomain,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${restaurant.smallArea.name}[${restaurant.largeArea.name}]",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = restaurant.genre.name,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = restaurant.budget.name,
            style = MaterialTheme.typography.bodyMedium
        )
        Row {
            Text(
                text = restaurant.access,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ErrorContent(
    @StringRes messageResId: Int,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = messageResId))
        if (onRetry != null) {
            Button(onClick = onRetry) {
                Text(text = stringResource(R.string.common_retry))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RestaurantDetailTopAppBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.restaurant_list_search_result_top_bar_title)) },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
    )
}
