package com.ferpa.argentinacampeon.presentation.select_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Constants.MATCHES
import com.ferpa.argentinacampeon.common.Constants.PLAYERS
import com.ferpa.argentinacampeon.domain.model.getItemTitle
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradient
import com.ferpa.argentinacampeon.presentation.select_list.components.PlayerItemCard
import com.ferpa.argentinacampeon.presentation.select_list.components.TopNavigationBar
import com.ferpa.argentinacampeon.presentation.versus.components.TagCard
import com.google.firebase.analytics.FirebaseAnalytics

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun SelectListScreen(
    navController: NavController,
    firebaseAnalytics: FirebaseAnalytics,
    viewModel: SelectListViewModel = hiltViewModel()
) {
    val listState = viewModel.selectListState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val lazyGridState = rememberLazyGridState()

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
            content = {

                Surface(elevation = MaterialTheme.spacing.default) {

                    TopNavigationBar(items =
                    listOf(
                        TopNavItem(
                            name = stringResource(id = R.string.matches),
                            route = Screen.SelectListScreenRoute.createRoute(MATCHES),
                            icon = Icons.Default.SportsSoccer
                        ),
                        TopNavItem(
                            name = stringResource(id = R.string.players),
                            route = Screen.SelectListScreenRoute.createRoute(PLAYERS),
                            icon = Icons.Default.Label
                        ),
                        /*
                        TopNavItem(
                            name = stringResource(id = R.string.photographers),
                            route = Screen.SelectListScreenRoute.createRoute(PHOTOGRAPHERS),
                            icon = Icons.Default.PhotoCamera
                        ),
                        TopNavItem(
                            name = stringResource(id = R.string.tags),
                            route = Screen.SelectListScreenRoute.createRoute(TAGS),
                            icon = Icons.Default.Tag
                        )
                         */
                    ),
                        navController = navController,
                        onItemClick = {
                            viewModel.setPage(it.route)
//                            navController.navigate(it.route)
                        })
                }

            })

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = {
                val gridCells = remember { mutableStateOf(1) }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(),
                    columns = GridCells.Fixed(gridCells.value),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                    state = lazyGridState
                ) {
                    if (!listState.matches.isNullOrEmpty()) {
                        gridCells.value = 1
                        items(listState.matches.size) { index ->
                            val match = listState.matches[index]
                            Surface(elevation = MaterialTheme.spacing.default, color = Color.Black) {
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = MaterialTheme.spacing.medium)
                                        .clickable {
                                            navController.navigate(
                                                Screen.PhotoListByMatchScreenRoute.createRoute(
                                                    match.id
                                                )
                                            )
                                        },
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        val drawable = when (index) {
                                            0 -> R.drawable.arabia
                                            1 -> R.drawable.mexico
                                            2 -> R.drawable.polonia
                                            3 -> R.drawable.octavos
                                            4 -> R.drawable.cuartos
                                            5 -> R.drawable.semifinal
                                            6 -> R.drawable.fideo
                                            7 -> R.drawable.la_tercera
                                            8 -> R.drawable.ar
                                            else -> R.drawable.recibimiento2
                                        }
                                        GlideImage(
                                            modifier = Modifier.fillMaxSize(),
                                            model = drawable,
//                                            model = match.extra ?: "",
                                            contentDescription = "match",
                                            contentScale = ContentScale.FillWidth
                                        )
                                        Text(
                                            text = match.getItemTitle().uppercase(),
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color.White,
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = MaterialTheme.spacing.default,
                                                    vertical = MaterialTheme.spacing.extraSmall
                                                )
                                                .fillMaxWidth(),
                                            overflow = TextOverflow.Ellipsis,
                                            style = TextStyle(
                                                shadow = Shadow(
                                                    color = Constants.VioletDark,
                                                    offset = Offset(2.5f, 2.0f),
                                                    blurRadius = 2f
                                                ),
                                                fontFamily = FontFamily(Font(R.font.qatar)),
                                                fontWeight = FontWeight.Light,
                                                letterSpacing = 10.sp
                                            )
                                        )
                                    })
                            }
                        }
                    } else if (!listState.players.isNullOrEmpty()) {
                        items(listState.players.size) { index ->
                            gridCells.value = 2
                            val player = listState.players[index]
                            PlayerItemCard(
                                player = player,
                                onPlayerClick = {
                                    navController.navigate(
                                        Screen.PhotoListByPlayerScreenRoute.createRoute(
                                            it
                                        )
                                    )
                                }
                            )
                        }
                    } else if (!listState.photographers.isNullOrEmpty()) {
                        items(listState.photographers.size) { index ->
                            val photographer = listState.photographers[index]
                            Text(text = photographer.name ?: "")
                        }
                    } else if (!listState.tags.isNullOrEmpty()) {
                        items(listState.tags.size) { index ->
                            val tag = listState.tags[index]
                            TagCard(tag = tag,
                                onTagClick = {
                                    navController.navigate(
                                        Screen.PhotoListByTagScreenRoute.createRoute(
                                            it
                                        )
                                    )
                                })
                        }
                    }
                }
            })
            if (listState.error.isNotBlank()) {
                Text(
                    text = listState.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .align(Alignment.Center)
                )
            }
            if (listState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomGradient(0.01f)
            }
        }

    }
}