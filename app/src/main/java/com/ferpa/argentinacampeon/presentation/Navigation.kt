package com.ferpa.argentinacampeon.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.presentation.best_photos.BestPhotosScreen
import com.ferpa.argentinacampeon.presentation.favorites.FavoritePhotosScreen
import com.ferpa.argentinacampeon.presentation.insertData.InsertDataScreen
import com.ferpa.argentinacampeon.presentation.main_activity.MainViewModel
import com.ferpa.argentinacampeon.presentation.photo_detail.PhotoDetailScreen
import com.ferpa.argentinacampeon.presentation.photo_list.PhotoListScreen
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.VersusScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Navigation(navController: NavHostController, mainViewModel: MainViewModel, pagerState: PagerState) {
    NavHost(navController = navController, startDestination = Screen.VersusScreenRoute.route) {
        composable(route = Screen.VersusScreenRoute.route) {
            VersusScreen(navController, mainViewModel =  mainViewModel, pagerState = pagerState)
        }
        composable(route = Screen.PhotoDetailScreenRoute.route) {
            PhotoDetailScreen(navController)
        }
        composable(route = Screen.PhotoListScreenRoute.route) {
            PhotoListScreen(navController)
        }
        composable(route = Screen.PhotoListByPlayerScreenRoute.route) {
            PhotoListScreen(navController)
        }
        composable(route = Screen.PhotoListByTagScreenRoute.route) {
            PhotoListScreen(navController)
        }
        composable(route = Screen.PhotoListByMatchScreenRoute.route) {
            PhotoListScreen(navController)
        }
        composable(route = Screen.BestPhotosScreenRoute.route) {
            BestPhotosScreen(navController = navController)
        }
        composable(route = Screen.FavoritePhotosScreenRoute.route) {
            FavoritePhotosScreen(navController)
        }
        composable(route = Screen.StoryScreenRoute.route) {
            InsertDataScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = Modifier,
        backgroundColor = Constants.VioletDark,
        elevation = 8.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.White,
                unselectedContentColor = Constants.LightBlue,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Icon(imageVector = item.icon, contentDescription = item.name)
                            }
                        } else {
                            Icon(imageVector = item.icon, contentDescription = item.name)
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                })
        }
    }
}