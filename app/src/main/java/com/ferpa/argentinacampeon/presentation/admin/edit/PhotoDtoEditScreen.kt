package com.ferpa.argentinacampeon.presentation.admin.edit

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.data.remote.dto.getPhotoUrl
import com.ferpa.argentinacampeon.data.remote.dto.getRank
import com.ferpa.argentinacampeon.domain.model.getMinimalTitle
import com.ferpa.argentinacampeon.domain.model.toMatchTitle
import com.ferpa.argentinacampeon.domain.model.toPhotographerTitle
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradient
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.presentation.versus.components.TagRow


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PhotoDtoEditScreen(
    navController: NavController,
    viewModel: AdminPhotoDtoEditViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val photoDtoState = viewModel.photoDtoState.value
    val playersList = viewModel.playersList.value.players
    val matchesList = viewModel.matchesListState.value.matches
    val phList = viewModel.photographersListState.value.photographers
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Constants.VioletDark)
    ) {
        Surface(color = Color.White) {
            val photoDetail = photoDtoState.photo
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState, true),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    model = photoDetail?.getPhotoUrl(),
                    contentDescription = photoDetail?.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
//                            onPhotoClick()
                        },
                    contentScale = ContentScale.FillWidth
                )
                Text(text = photoDetail?.photoUrl.toString(), color = Color.Black)
                Text(text = photoDetail?.match?.getMinimalTitle() ?: "null", color = Color.Black)

                val mathesScrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(mathesScrollState, true),
                    horizontalArrangement = Arrangement.Center
                ) {
                    matchesList?.forEach {
                        MatchTextEdit(matchTitle = it.toMatchTitle(), onMatchClick = {
                            viewModel.setMatch(it)
                        })
                    }
                }

                Text(text = "Added")
                PlayerRowEdit(players = photoDtoState.photo?.players, onPlayerClick = {
                    viewModel.removePlayer(it)
                })
                Text(text = "toAdd")
                if (playersList != null) {
                    PlayerRowEdit(players = playersList, onPlayerClick = {
                        viewModel.addPlayer(it)
                    })
                }

                Text(
                    text = photoDetail?.description ?: "",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                )

                Text("${photoDetail?.photographer?.name}", color = Color.Black)
                val phScrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(phScrollState, true),
                    horizontalArrangement = Arrangement.Center
                ) {
                    phList?.forEach { it ->
                        PhCardEdit(photographerTitle = it.toPhotographerTitle(), onPhotographerClick = {
                            viewModel.setPhotographer(photographerTitle = it)
                        })
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text("votes: ${photoDetail?.votes}", color = Color.Black)
                    Text("versus: ${photoDetail?.versus}", color = Color.Black)
                    Text("rank: ${photoDetail?.getRank()}", color = Color.Black)
                }
                val votesScrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(votesScrollState, true),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { viewModel.setVotesAndVersus(4, 16) }, modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                        Text(text = "4/16", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setVotesAndVersus(4, 13) }, modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                        Text(text = "5/13", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setVotesAndVersus(8, 16) }, modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                        Text(text = "8/16", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setVotesAndVersus(16, 25) }, modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                        Text(text = "16/25", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setVotesAndVersus(18, 25) }, modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                        Text(text = "18/25", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setVotesAndVersus(14, 18) }, modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                        Text(text = "14/18", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setVotesAndVersus(13, 16) }, modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                        Text(text = "13/16", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setVotesAndVersus(20, 22) }, modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                        Text(text = "20/22", fontSize = 10.sp)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { viewModel.setState(0) }) {
                        Text(text = "disp", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setState(-1) }) {
                        Text(text = "ocultas", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setState(-2) }) {
                        Text(text = "listas", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setState(-3) }) {
                        Text(text = "incomp", fontSize = 10.sp)
                    }
                    Button(onClick = { viewModel.setState(-4) }) {
                        Text(text = "elim", fontSize = 10.sp)
                    }
                }
                Button(onClick = { viewModel.fullUpdate() }) {
                    Text(text = "guardar", fontSize = 10.sp)
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
            Log.d("PhotoDetail", photoDtoState.photo?.photoUrl.toString())
            if (photoDtoState.error.isNotBlank()) {
                Text(
                    text = photoDtoState.error,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .align(Alignment.Center)
                )
            }
            if (photoDtoState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomGradient()
            }
        }
    }
}
