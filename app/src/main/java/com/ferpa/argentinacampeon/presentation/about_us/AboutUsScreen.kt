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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.common.AnalyticsEvents.ABOUT_US_LINK_CLICK
import com.ferpa.argentinacampeon.common.AnalyticsEvents.FORCE_UPDATE_CLICK
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Extensions.appVersion
import com.ferpa.argentinacampeon.common.Extensions.logSingleEvent
import com.ferpa.argentinacampeon.domain.model.ServerInfo
import com.ferpa.argentinacampeon.presentation.common.components.BottomGradient
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
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
                        items(listState.serverInfo.size) { index ->
                            val info = listState.serverInfo[index] ?: return@items
                            AppInfoBlock(info = info, firebaseAnalytics)
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppInfoBlock(
    info: ServerInfo,
    firebaseAnalytics: FirebaseAnalytics? = null,
    context: Context = LocalContext.current,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    isAMessage: String = ""
) {
    if (isAMessage.isNotEmpty()) firebaseAnalytics?.logSingleEvent(isAMessage)
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = horizontalAlignment,
        modifier = Modifier.padding(paddingValues)
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
        if (!info.title.isNullOrEmpty() && info.title != "minVersion") {
            if (isAMessage.isEmpty()) Divider(color = Constants.LightBlueTransparent)
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
                textAlign = TextAlign.Justify
            )
        }
        if (!info.link.isNullOrEmpty()) {
            if (info.dateCondition != null && info.dateCondition.contains("button")) {
                Card(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.default),
                    shape = MaterialTheme.shapes.small,
                    backgroundColor = Constants.LightBlue,
                    elevation = 8.dp
                ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            firebaseAnalytics?.let { it.logSingleEvent(FORCE_UPDATE_CLICK) }
                            contactUs(info.link, context)
                        },
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                        Box(modifier = Modifier.padding(MaterialTheme.spacing.default)) {
                            info.iconUrl?.let {
                                GlideImage(
                                    model = it,
                                    contentDescription = info.title,
                                    modifier = Modifier
                                        .size(Constants.ICON_SIZE.dp),
                                    contentScale = ContentScale.Fit,
                                )
                            }
                        }
                        Text(
                            text = info.dateCondition.split("=").last(),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(end = MaterialTheme.spacing.default),
                            textAlign = TextAlign.Center,
                            letterSpacing = 1.sp
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.spacing.default)
                        .clickable {
                            firebaseAnalytics?.let { it.logSingleEvent(ABOUT_US_LINK_CLICK) }
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
                            .padding(MaterialTheme.spacing.default),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
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