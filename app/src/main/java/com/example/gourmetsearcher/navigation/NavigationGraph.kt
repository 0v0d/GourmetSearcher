package com.example.gourmetsearcher.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gourmetsearcher.model.data.decodeSearchTerms
import com.example.gourmetsearcher.model.data.encodeSearchTerms
import com.example.gourmetsearcher.model.domain.decodeRestaurantData
import com.example.gourmetsearcher.model.domain.encodeRestaurantData
import com.example.gourmetsearcher.ui.screen.inputkeyword.InputKeyWordScreen
import com.example.gourmetsearcher.ui.screen.restaurantdetail.RestaurantDetailScreen
import com.example.gourmetsearcher.ui.screen.restaurantlist.RestaurantListScreen
import com.example.gourmetsearcher.ui.screen.seachlocation.SearchLocationScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationGraph.INPUT_KEYWORD_SCREEN.name,
    ) {
        addInputKeywordScreen(navController)
        addSearchLocationScreen(navController)
        addRestaurantListScreen(navController)
        addRestaurantDetailScreen(navController)
    }
}

private fun NavGraphBuilder.addInputKeywordScreen(navController: NavHostController) {
    composable(NavigationGraph.INPUT_KEYWORD_SCREEN.name) {
        InputKeyWordScreen(
            onNavigateToSearchLocation = { inputText, range ->
                val safeName = Uri.encode(inputText)
                navController.navigateSingleTopTo(
                    "${NavigationGraph.SEARCH_LOCATION_SCREEN.name}/$safeName,$range"
                )
            }
        )
    }
}

private fun NavGraphBuilder.addSearchLocationScreen(navController: NavHostController) {
    composable(
        route = "${NavigationGraph.SEARCH_LOCATION_SCREEN.name}/{inputText},{range}",
        arguments = listOf(
            navArgument("inputText") { type = NavType.StringType },
            navArgument("range") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        SearchLocationScreen(
            inputText = backStackEntry.arguments?.getString("inputText") ?: "",
            range = backStackEntry.arguments?.getInt("range") ?: 0,
            onClick = {
                navController.popBackStack()
            },
            onNavigateToResultList = { searchTerms ->
                val encodedSearchTerms = encodeSearchTerms(searchTerms)
                navController.navigateSingleTopTo(
                    "${NavigationGraph.RESTAURANT_LIST_SCREEN.name}/$encodedSearchTerms"
                )
            }
        )
    }
}

private fun NavGraphBuilder.addRestaurantListScreen(navController: NavHostController) {
    composable(
        route = "${NavigationGraph.RESTAURANT_LIST_SCREEN.name}/{searchTerms}",
        arguments = listOf(navArgument("searchTerms") { type = NavType.StringType })
    ) { backStackEntry ->
        val encodedSearchTerms = backStackEntry.arguments?.getString("searchTerms") ?: ""
        val searchTerms = decodeSearchTerms(encodedSearchTerms)

        RestaurantListScreen(
            searchTerms = searchTerms,
            onClick = {
                navController.popBackStack(
                    NavigationGraph.INPUT_KEYWORD_SCREEN.name,
                    inclusive = false
                )
            },
            onNavigateToDetail = { shopsDomain ->
                val encodedRestaurantData = encodeRestaurantData(shopsDomain)
                navController.navigateSingleTopTo(
                    "${NavigationGraph.RESTAURANT_DETAIL_SCREEN.name}/$encodedRestaurantData"
                )
            }
        )
    }
}

private fun NavGraphBuilder.addRestaurantDetailScreen(navController: NavHostController) {
    composable(
        route = "${NavigationGraph.RESTAURANT_DETAIL_SCREEN.name}/{restaurantData}",
        arguments = listOf(
            navArgument("restaurantData") { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val encodedRestaurantData = backStackEntry.arguments?.getString("restaurantData") ?: ""
        val restaurantData = decodeRestaurantData(encodedRestaurantData)
        RestaurantDetailScreen(
            restaurantData = restaurantData,
            onClick = {
                navController.popBackStack()
            }
        )
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }

private enum class NavigationGraph {
    INPUT_KEYWORD_SCREEN,
    SEARCH_LOCATION_SCREEN,
    RESTAURANT_LIST_SCREEN,
    RESTAURANT_DETAIL_SCREEN,
}
