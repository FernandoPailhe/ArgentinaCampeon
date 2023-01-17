package com.ferpa.argentinacampeon.presentation.about_us

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.common.AnalyticsEvents
import com.ferpa.argentinacampeon.common.AnalyticsEvents.ABOUT_US_LINK_CLICK
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.appVersion
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradient
import com.google.firebase.analytics.FirebaseAnalytics

@SuppressLint("QueryPermissionsNeeded")
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun AboutUsScreen(
    navController: NavController,
    firebaseAnalytics: FirebaseAnalytics,
    viewModel: AboutUsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val version = context.appVersion()
    val listState = viewModel.aboutUsInfo.value
    Surface(
        color = Constants.VioletDark
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val lazyGridState = rememberLazyGridState()
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
                content = {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = MaterialTheme.spacing.large,
                                vertical = MaterialTheme.spacing.default
                            ),
                        columns = GridCells.Fixed(1),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                        state = lazyGridState
                    ) {
                        item {
                            Text(
                                text = "App Version: $version",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.default),
                            )
                        }
                        items(listState.infoFromApi.size) { index ->
                            val info = listState.infoFromApi[index] ?: return@items
                            Column(
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                if (!info.photoUrl.isNullOrEmpty()) {
                                    GlideImage(
                                        model = info.photoUrl,
                                        contentDescription = info.title,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                                if (!info.title.isNullOrEmpty()) {
                                    Text(
                                        text = info.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier
                                            .padding(MaterialTheme.spacing.default)
                                    )
                                }
                                if (!info.content.isNullOrEmpty()) {
                                    Text(
                                        text = info.content,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White,
                                        modifier = Modifier
                                            .padding(MaterialTheme.spacing.default),
                                    )
                                }
                                if (!info.link.isNullOrEmpty()) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = MaterialTheme.spacing.default)
                                            .clickable {
                                                firebaseAnalytics.logSingleEvent(ABOUT_US_LINK_CLICK)
                                                contactUs(info.link, context)
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = Modifier.padding(start = MaterialTheme.spacing.small)) {
                                            info.iconUrl?.let {
                                                GlideImage(
                                                    model = it,
                                                    contentDescription = info.title,
                                                    modifier = Modifier
                                                        .size(Constants.ICON_SIZE.dp)
                                                        .padding(MaterialTheme.spacing.small),
                                                    contentScale = ContentScale.Fit,
                                                )
                                            }
                                        }
                                        Text(
                                            text = info.link,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Light,
                                            color = Color.White,
                                            modifier = Modifier
                                                .padding(MaterialTheme.spacing.default)
                                        )
                                    }
                                }
                            }
                        }
                    }
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
                        BottomGradient(0.02f)
                    }
                })
        }
    }
}

fun contactUs(link: String, context: Context) {
    try {
        if (PatternsCompat.EMAIL_ADDRESS
                .matcher(link)
                .matches()
        ) {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(link.trim())
            )
            context.startActivity(intent)
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(link)
            context.startActivity(intent)
        }
    } catch (e: Exception) {
        Log.e(
            "Intent",
            e.localizedMessage
                ?: "An unexpected error occurred"
        )
    }
}