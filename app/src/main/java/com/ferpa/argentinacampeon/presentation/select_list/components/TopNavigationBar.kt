package com.ferpa.argentinacampeon.presentation.select_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceEvenly
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.ferpa.argentinacampeon.presentation.BottomNavItem
import com.ferpa.argentinacampeon.presentation.about_us.AboutUsScreen
import com.ferpa.argentinacampeon.presentation.best_photos.BestPhotosScreen
import com.ferpa.argentinacampeon.presentation.favorites.FavoritePhotosScreen
import com.ferpa.argentinacampeon.presentation.insertData.InsertDataScreen
import com.ferpa.argentinacampeon.presentation.main_activity.MainViewModel
import com.ferpa.argentinacampeon.presentation.photo_detail.PhotoDetailScreen
import com.ferpa.argentinacampeon.presentation.photo_list.PhotoListScreen
import com.ferpa.argentinacampeon.presentation.select_list.SelectListScreen
import com.ferpa.argentinacampeon.presentation.select_list.TopNavItem
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.VersusScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.firebase.analytics.FirebaseAnalytics

@Composable
fun TopNavigationBar(
    items: List<TopNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (TopNavItem) -> Unit
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
//                    Column(horizontalAlignment = CenterHorizontally) {
                    Row(verticalAlignment = CenterVertically, horizontalArrangement = SpaceBetween){
                        /*
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                         */
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Icon(imageVector = item.icon, contentDescription = item.name)
                            }
                        } else {
                            Icon(imageVector = item.icon, contentDescription = item.name)
                        }
                        Text(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                            text = item.name,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                })
        }
    }
}