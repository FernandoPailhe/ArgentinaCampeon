package com.ferpa.argentinacampeon.presentation.tutorial


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.presentation.common.components.WelcomeBackdrop
import com.ferpa.argentinacampeon.presentation.main_activity.MainViewModel
import com.ferpa.argentinacampeon.presentation.tutorial.component.PairPhotoItemTutorial
import com.ferpa.argentinacampeon.presentation.tutorial.component.VersusScreenTutorial
import com.ferpa.argentinacampeon.presentation.versus.VersusViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: MainViewModel,
    tutorialViewModel: TutorialViewModel = hiltViewModel(),
    versusViewModel: VersusViewModel = hiltViewModel(),
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val textOffset = (screenHeight / 5.5f)
    val infoList = viewModel.tutorialInfo.value.infoFromApi
    tutorialViewModel.getTutorialPairList()
    val userScrollEnabled = remember { mutableStateOf(true) }
    val slideOutBackDrop = remember { mutableStateOf(false) }
    val verticalPagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val pairList = tutorialViewModel.versusListState.value.photos

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Constants.VioletDark
    ) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center,
            content = {
                if (infoList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxHeight()) {
                        WelcomeBackdrop(
                            drawableResource = R.drawable.coronacion_cerca,
                            offset = 0.0f,
                            page = 0,
                            slideOut = slideOutBackDrop.value
                        )
                    }
                }
                if (viewModel.tutorialInfo.value.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
                if (infoList.isNotEmpty()) {
                    val infoListPosition = remember {mutableStateOf(verticalPagerState.currentPage + 3)}
                    VerticalPager(
                        count = pairList.size,
                        state = verticalPagerState,
                        userScrollEnabled = false,
                        modifier = Modifier.fillMaxSize()
                    ) { verticalPage ->
                        val horizontalPagerState = rememberPagerState()
                        if (pairList.isEmpty()) {
                            return@VerticalPager
                        } else {
                            pairList[verticalPage]?.let { versusPair ->
                                PairPhotoItemTutorial(
                                    modifier = Modifier.fillMaxSize(),
                                    photoPair = versusPair,
                                    onPhotoClick = {
                                        try {
                                            scope.launch {
                                                infoListPosition.value ++
                                                delay(Constants.POST_VOTE_DELAY)
                                                verticalPagerState.animateScrollToPage(verticalPage + 1)
                                            }
                                        } catch (e: Exception) {
                                            Log.e(
                                                "VersusScreen",
                                                e.localizedMessage ?: "Unknown error"
                                            )
                                        }
                                        versusViewModel.postVote(it, true)
                                    },
                                    onButtonClicked = { viewModel.setFirstTimeFalse() },
                                    infoContent = if (verticalPagerState.currentPage == 1) infoList[3]?.content ?: "" else infoList[4]?.content ?: ""
                                )
                                if (verticalPagerState.currentPage == 0 && verticalPagerState.currentPageOffset < 1.0f) {
                                    Box(modifier = Modifier.fillMaxHeight()) {
                                        WelcomeBackdrop(
                                            drawableResource = R.drawable.coronacion_cerca,
                                            offset = horizontalPagerState.currentPageOffset,
                                            page = horizontalPagerState.currentPage,
                                            slideOut = slideOutBackDrop.value
                                        )
                                    }
                                    HorizontalPager(
                                        count = infoList.size,
                                        state = horizontalPagerState,
                                        userScrollEnabled = userScrollEnabled.value
                                    ) { horizontalPage ->
                                        infoList[horizontalPage]?.let { info ->
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(vertical = MaterialTheme.spacing.large),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                if (horizontalPagerState.currentPage == 1) tutorialViewModel.getTutorialPairList()
                                                if (horizontalPagerState.currentPage == 2 && horizontalPagerState.currentPageOffset == 0.0f) {
                                                    userScrollEnabled.value = false
                                                }
                                                /*
                                                if (horizontalPagerState.currentPage == 3) {

                                                    userScrollEnabled.value = false
                                                    slideOutBackDrop.value = true
                                                    VersusScreenTutorial(
                                                        tutorialViewModel = tutorialViewModel,
                                                        onButtonClicked = {
                                                            viewModel.setFirstTimeFalse()
                                                        },
                                                        infoFromApiList = infoList
                                                    )

                                                } else {

                                                 */
                                                Text(
                                                    text = info.content ?: "",
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    style = TextStyle(
                                                        shadow = Shadow(
                                                            color = Constants.VioletDark,
                                                            offset = Offset(2.5f, 2.0f),
                                                            blurRadius = 2f
                                                        ),
                                                        fontFamily = FontFamily(Font(R.font.qatar)),
                                                        fontWeight = FontWeight.Normal,
                                                    ),
                                                    modifier = Modifier
                                                        .padding(
                                                            horizontal = MaterialTheme.spacing.default,
                                                            vertical = MaterialTheme.spacing.medium
                                                        )
                                                        .fillMaxWidth()
                                                        .offset(y = textOffset),
                                                )
                                                if (horizontalPagerState.currentPage == 2) {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .offset(y = textOffset),
                                                        contentAlignment = Alignment.Center,
                                                        content = {
                                                            Card(
                                                                modifier = Modifier
                                                                    .padding(MaterialTheme.spacing.medium)
                                                                    .clickable {
                                                                        scope.launch {
                                                                            delay(Constants.POST_VOTE_DELAY/2)
                                                                            verticalPagerState.animateScrollToPage(
                                                                                verticalPage + 1
                                                                            )
                                                                        }
                                                                    },
                                                                shape = MaterialTheme.shapes.small,
                                                                backgroundColor = Constants.LightBlue,
                                                                elevation = 8.dp
                                                            ) {
                                                                Text(
                                                                    text = if (info.title.isNullOrEmpty()) "OK" else info.title,
                                                                    fontWeight = FontWeight.SemiBold,
                                                                    color = Color.White,
                                                                    fontSize = 16.sp,
                                                                    textAlign = TextAlign.Justify,
                                                                    modifier = Modifier
                                                                        .padding(
                                                                            horizontal = MaterialTheme.spacing.medium,
                                                                            vertical = MaterialTheme.spacing.default
                                                                        )
                                                                )
                                                            }
                                                        })
                                                }
//                                            }
                                                Log.d(
                                                    "current page",
                                                    horizontalPagerState.currentPage.toString()
                                                )
                                                if (horizontalPagerState.currentPage < 3) {
                                                    /*
                                                    Text(
                                                        text = "✭     ✭     ✭",
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        textAlign = TextAlign.Center,
                                                        color = Color.White,
                                                        modifier = Modifier
                                                            .padding(
                                                                horizontal = MaterialTheme.spacing.default,
                                                                vertical = MaterialTheme.spacing.medium
                                                            )
                                                            .fillMaxWidth(),
                                                    )

                                                     */
                                                }
                                            }
                                        }
                                    }

                                    if (horizontalPagerState.currentPage < 3) {
                                        val rowStarPageOffset =
                                            (horizontalPagerState.currentPage * 40)
                                        val rowStarCurrentOffset =
                                            (horizontalPagerState.currentPageOffset * 40)
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.BottomCenter,
                                            content = {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(vertical = MaterialTheme.spacing.large)
//                                .offset(x = 40.dp - rowStarPageOffset.dp - rowStarCurrentOffset.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Star,
                                                        contentDescription = "star",
                                                        tint = Color.White,
                                                        modifier = Modifier
                                                            .padding(
                                                                horizontal = MaterialTheme.spacing.default
                                                            )
                                                    )
                                                    Icon(
                                                        imageVector = Icons.Default.Star,
                                                        contentDescription = "star",
                                                        tint = Color.White,
                                                        modifier = Modifier
                                                            .padding(
                                                                horizontal = MaterialTheme.spacing.default
                                                            )
                                                    )
                                                    Icon(
                                                        imageVector = Icons.Default.Star,
                                                        contentDescription = "star",
                                                        tint = Color.White,
                                                        modifier = Modifier
                                                            .padding(
                                                                horizontal = MaterialTheme.spacing.default
                                                            )
                                                    )
                                                }
                                                Icon(
                                                    imageVector = Icons.Default.Star,
                                                    contentDescription = "star",
                                                    tint = Color.Yellow,
                                                    modifier = Modifier
                                                        .offset(x = -(40).dp + rowStarPageOffset.dp + rowStarCurrentOffset.dp)
                                                        .padding(
                                                            vertical = MaterialTheme.spacing.large,
                                                            horizontal = MaterialTheme.spacing.default
                                                        )
                                                )
                                            })
                                    }
                                }
                            }
                        }
                    }
                }
            })
    }
}