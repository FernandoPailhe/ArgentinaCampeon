package com.ferpa.argentinacampeon.presentation.main_activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.AnalyticsEvents.FIRST_OPEN
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.common.LockScreenOrientation
import com.ferpa.argentinacampeon.presentation.BottomNavItem
import com.ferpa.argentinacampeon.presentation.BottomNavigationBar
import com.ferpa.argentinacampeon.presentation.Navigation
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.about_us.AppInfoBlock
import com.ferpa.argentinacampeon.presentation.tutorial.WelcomeScreen
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var keepSplashScreen = true

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isFirstTime.value.isLoading && keepSplashScreen && viewModel.updateState.value.updateLocalMatchListState.isLoading
            }
        }
        setContent {
            BestQatar2022PhotosTheme {
                firebaseAnalytics = FirebaseAnalytics.getInstance(LocalContext.current)
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
                    if (viewModel.isFirstTime.value == UpdateLocalState(succes = false) && viewModel.versionOk.value) {
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
                                BottomNavItem(
                                    name = "List",
                                    route = Screen.PhotoListScreenRoute.route,
                                    icon = Icons.Default.List
                                ),*/
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
                                    pagerState,
                                    onDataLoaded = {
                                        keepSplashScreen = false
                                    },
                                    firebaseAnalytics = firebaseAnalytics
                                )
                            }
                        }
                    } else if (!viewModel.versionOk.value) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Constants.VioletDark)
                        ) {
                            Dialog(onDismissRequest = { }) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxHeight(0.5f)
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(MaterialTheme.spacing.medium)
                                ) {
                                    Box(modifier = Modifier
                                        .background(Constants.VioletDark),
                                        contentAlignment = Alignment.Center,
                                        content = {
                                            AppInfoBlock(
                                                info = viewModel.forceUpdateVersion.value,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.medium)
                                            )
                                        })
                                }
                            }
                        }
                    } else {
                        firebaseAnalytics.logSingleEvent(FIRST_OPEN)
                        WelcomeScreen(
                            navController = navController,
                            viewModel = viewModel,
                            onDataLoaded = {
                                keepSplashScreen = false
                            }, firebaseAnalytics = firebaseAnalytics
                        )
                    }
                }
            }
        }
    }
}


