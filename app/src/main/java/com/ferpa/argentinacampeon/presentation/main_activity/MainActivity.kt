package com.ferpa.argentinacampeon.presentation.main_activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.presentation.BottomNavItem
import com.ferpa.argentinacampeon.presentation.BottomNavigationBar
import com.ferpa.argentinacampeon.presentation.Navigation
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.versus.VersusScreenPreview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.LockScreenOrientation
import com.ferpa.argentinacampeon.presentation.tutorial.WelcomeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                (viewModel.versusListState.value.isLoading &&
                        viewModel.versusListState.value.photos.isEmpty() &&
                        viewModel.favoriteState.value.isLoading)
            }
        }
        setContent {
            BestQatar2022PhotosTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Constants.VioletDark,
                    darkIcons = false
                )
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Constants.VioletDark
                ) {
                    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    val navController = rememberNavController()
                    if (!viewModel.isFirstTime.value) {
                        Scaffold(bottomBar = {
                            BottomNavigationBar(items = listOf(
                                /*
                                BottomNavItem(
                                    name = "Story",
                                    route = Screen.StoryScreenRoute.route,
                                    icon = Icons.Default.MoreVert
                                ),

                            BottomNavItem(
                                name = "Detail",
                                route = Screen.PhotoDetailScreenRoute.route,
                                icon = Icons.Default.Done
                            ),
                             */ BottomNavItem(
                                    name = "List",
                                    route = Screen.PhotoListScreenRoute.route,
                                    icon = Icons.Default.List
                                ),
                                BottomNavItem(
                                    name = stringResource(id = R.string.best),
                                    route = Screen.BestPhotosScreenRoute.route,
                                    icon = Icons.Default.Star
                                ),
                                BottomNavItem(
                                    name = stringResource(id = R.string.versus),
                                    route = Screen.VersusScreenRoute.route,
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = stringResource(id = R.string.favorites),
                                    route = Screen.FavoritePhotosScreenRoute.route,
                                    icon = Icons.Default.Bookmarks
                                ),
                                BottomNavItem(
                                    name = "Info",
                                    route = Screen.AboutUsScreenRoute.route,
                                    icon = Icons.Default.Info
                                ),
                            ), navController = navController, onItemClick = {
                                navController.navigate(it.route)
                            })
                        }) { innerPadding ->
                            Box(
                                modifier = Modifier.padding(
                                    PaddingValues(
                                        0.dp, 0.dp, 0.dp, innerPadding.calculateBottomPadding()
                                    )
                                )
                            ) {
                                val pagerState = rememberPagerState()
                                Navigation(
                                    navController = navController,
                                    mainViewModel = viewModel,
                                    pagerState
                                )
                            }
                        }
                    } else {
                        WelcomeScreen(navController = navController, viewModel)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BestQatar2022PhotosTheme {
        VersusScreenPreview()
    }
}

