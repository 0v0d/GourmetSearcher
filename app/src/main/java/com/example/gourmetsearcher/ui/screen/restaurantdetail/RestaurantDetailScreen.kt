package com.example.gourmetsearcher.ui.screen.restaurantdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.gourmetsearcher.R
import com.example.gourmetsearcher.model.domain.ShopsDomain
import com.example.gourmetsearcher.utils.openMap
import com.example.gourmetsearcher.utils.openWebBrowser
import com.example.gourmetsearcher.viewmodel.RestaurantDetailViewModel

@Composable
fun RestaurantDetailScreen(
    onClick: () -> Unit,
    restaurantData: ShopsDomain,
    modifier: Modifier = Modifier,
    viewModel: RestaurantDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val address by viewModel.address.collectAsState()
    val url by viewModel.url.collectAsState()

    LaunchedEffect(address) {
        address?.let {
            openMap(context, it)
            viewModel.clearAddress()
        }
    }

    LaunchedEffect(url) {
        url?.let {
            openWebBrowser(context, it)
            viewModel.clearUrl()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            RestaurantDetailTopBar(
                title = restaurantData.name,
                onClick = onClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            RestaurantDetailContent(
                restaurant = restaurantData,
                onMapClick = { address -> viewModel.openMap(address) },
                onUrlClick = { url -> viewModel.openUrl(url) }
            )
        }
    }
}

@Composable
fun RestaurantDetailContent(
    restaurant: ShopsDomain,
    onMapClick: (String) -> Unit,
    onUrlClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(16.dp)) {
        Image(
            painter = rememberAsyncImagePainter(restaurant.photo.pc.l),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailItem(
            title = stringResource(R.string.restaurant_detail_restaurant_name),
            content = restaurant.name,
            icon = Icons.Default.Restaurant
        )
        DetailItem(
            title = stringResource(R.string.restaurant_detail_genre),
            content = restaurant.genre.name,
            icon = Icons.Default.DinnerDining
        )
        DetailItem(
            title = stringResource(R.string.restaurant_detail_address),
            content = restaurant.address,
            icon = Icons.Default.LocationOn
        )
        Button(
            onClick = { onMapClick(restaurant.address + restaurant.name) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Explore, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.restaurant_detail_map_description))
        }
        DetailItem(
            title = stringResource(R.string.restaurant_detail_access),
            content = restaurant.access,
            icon = Icons.AutoMirrored.Filled.DirectionsWalk
        )
        DetailItem(
            title = stringResource(R.string.restaurant_detail_budget),
            content = restaurant.budget.name,
            icon = Icons.Default.AttachMoney
        )
        DetailItem(
            title = stringResource(R.string.restaurant_detail_opening_hours),
            content = restaurant.open,
            icon = Icons.Default.Schedule
        )
        DetailItem(
            title = stringResource(R.string.restaurant_detail_closed_days),
            content = restaurant.close,
            icon = Icons.Default.EventBusy
        )
        Button(
            onClick = { onUrlClick(restaurant.url.pc) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Event, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.restaurant_detail_hot_pepper))
        }
    }
}

@Composable
private fun RestaurantDetailTopBar(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
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
private fun DetailItem(
    title: String,
    content: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = content)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
