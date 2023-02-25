package com.ferpa.argentinacampeon.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ferpa.argentinacampeon.common.AnalyticsEvents.NAV_FAVORITES
import com.ferpa.argentinacampeon.common.AnalyticsEvents.NAV_INFO
import com.ferpa.argentinacampeon.common.AnalyticsEvents.NAV_LIST
import com.ferpa.argentinacampeon.common.AnalyticsEvents.NAV_PHOTO_DETAIL
import com.ferpa.argentinacampeon.common.AnalyticsEvents.NAV_TOP
import com.ferpa.argentinacampeon.common.AnalyticsEvents.NAV_VERSUS
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.presentation.about_us.AboutUsScreen
import com.ferpa.argentinacampeon.presentation.best_photos.BestPhotosScreen
import com.ferpa.argentinacampeon.presentation.favorites.FavoritePhotosScreen
import com.ferpa.argentinacampeon.presentation.insertData.InsertDataScreen
import com.ferpa.argentinacampeon.presentation.main_activity.MainViewModel
import com.ferpa.argentinacampeon.presentation.photo_detail.PhotoDetailScreen
import com.ferpa.argentinacampeon.presentation.photo_list.PhotoListScreen
import com.ferpa.argentinacampeon.presentation.select_list.SelectListScreen
import com.ferpa.argentinacampeon.presentation.versus.VersusScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.firebase.analytics.FirebaseAnalytics

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Navigation(navController: NavHostController, mainViewModel: MainViewModel, pagerState: PagerState, onDataLoaded: () -> Unit, firebaseAnalytics: FirebaseAnalytics ) {
    NavHost(navController = navController, startDestination = Screen.VersusScreenRoute.route) {
        composable(route = Screen.VersusScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_VERSUS)
            VersusScreen(navController, mainViewModel =  mainViewModel, pagerState = pagerState, onDataLoaded = { onDataLoaded() }, firebaseAnalytics = firebaseAnalytics)
        }
        composable(route = Screen.PhotoDetailScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_PHOTO_DETAIL)
            PhotoDetailScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.SelectListScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_LIST)
            SelectListScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.SelectMatchesListScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_LIST)
            SelectListScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.SelectPlayersListScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_LIST)
            SelectListScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.SelectPhotographersListScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_LIST)
            SelectListScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.SelectTagsListScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_LIST)
            SelectListScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.PhotoListByPlayerScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_LIST)
            PhotoListScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.PhotoListByTagScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_LIST)
            PhotoListScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.PhotoListByMatchScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_LIST)
            PhotoListScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.BestPhotosScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_TOP)
            BestPhotosScreen(navController = navController, firebaseAnalytics)
        }
        composable(route = Screen.FavoritePhotosScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_FAVORITES)
            FavoritePhotosScreen(navController, firebaseAnalytics)
        }
        composable(route = Screen.AboutUsScreenRoute.route) {
            firebaseAnalytics.logSingleEvent(NAV_INFO)
            AboutUsScreen(navController, firebaseAnalytics)
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