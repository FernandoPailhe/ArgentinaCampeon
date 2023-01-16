package com.ferpa.argentinacampeon.presentation.tutorial.component


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ferpa.argentinacampeon.common.Constants.POST_VOTE_DELAY
import com.ferpa.argentinacampeon.domain.model.InfoFromApi
import com.ferpa.argentinacampeon.presentation.tutorial.TutorialViewModel
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.versus.VersusViewModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VersusScreenTutorial(
    modifier: Modifier = Modifier,
    tutorialViewModel: TutorialViewModel,
    versusViewModel: VersusViewModel = hiltViewModel(),
    onButtonClicked: () -> Unit,
    infoFromApiList: List<InfoFromApi?>
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val pairList = tutorialViewModel.versusListState.value.photos
    if (pairList.isEmpty()) return
    Box(
        modifier = Modifier
            .fillMaxHeight(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            VerticalPager(
                count = pairList.size,
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                if (pairList.isEmpty()) {
                    return@VerticalPager
                } else {
                    pairList[page]?.let { versusPair ->
                        PairPhotoItemTutorial(
                            modifier = Modifier.fillMaxSize(),
                            photoPair = versusPair,
                            onPhotoClick = {
                                try {
                                    scope.launch {
                                        delay(POST_VOTE_DELAY)
                                        pagerState.animateScrollToPage(page + 1)
                                    }
                                } catch (e: Exception) {
                                    Log.e(
                                        "VersusScreen",
                                        e.localizedMessage ?: "Unknown error"
                                    )
                                }
                                versusViewModel.postVote(it)
                            },
                            onButtonClicked = { onButtonClicked() },
                            infoContent = infoFromApiList[page + 3]?.content ?: ""
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun VersusScreenPreview() {
    BestQatar2022PhotosTheme {
//        VersusScreen(navController = rememberNavController())
    }
}