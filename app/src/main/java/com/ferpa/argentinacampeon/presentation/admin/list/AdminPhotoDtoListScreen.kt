package com.ferpa.argentinacampeon.presentation.admin.list


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.presentation.Screen
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing

@Composable
fun AdminPhotoDtoListScreen(
    navController: NavController,
    viewModel: AdminPhotoListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column() {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { viewModel.setState(0) }) {
                    Text(text = "disp", fontSize = 10.sp, color = Color.White)
                }
                Button(onClick = { viewModel.setState(-1) }) {
                    Text(text = "ocult", fontSize = 10.sp, color = Color.White)
                }
                Button(onClick = { viewModel.setState(-2) }) {
                    Text(text = "listas", fontSize = 10.sp, color = Color.White)
                }
                Button(onClick = { viewModel.setState(-3) }) {
                    Text(text = "incomp", fontSize = 10.sp, color = Color.White)
                }
                Button(onClick = { viewModel.setState(-4) }) {
                    Text(text = "elim", fontSize = 10.sp, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))
            val title = when (viewModel.rarity.value) {
                -1 -> "Ocultas"
                -2 -> "Listas"
                -3 -> "Incompletas"
                -4 -> "Eliminadas"
                else -> "Disponibles"
            }
            Text(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally), text = title, fontSize = 15.sp, color = Color.Black, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.photos.size) { index ->
                    CardPhotoDtoListItem(
                        photo = state.photos[index],
                        position = index + 1,
                        onItemClick = { photo ->
                            navController.navigate(
                                Screen.PhotoDtoEditScreen.createRoute(photo.id)
                            )
                        },
                        onPlayerClick = {
                            navController.navigate(
                                Screen.PhotoListByPlayerScreenRoute.createRoute(
                                    it
                                )
                            )
                        },
                        onMatchClick = {
                            navController.navigate(
                                Screen.PhotoListByMatchScreenRoute.createRoute(
                                    it
                                )
                            )
                        },
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
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Constants.VioletDark,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}