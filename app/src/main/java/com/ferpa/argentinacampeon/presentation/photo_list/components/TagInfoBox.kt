@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.ferpa.argentinacampeon.presentation.photo_list.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ferpa.argentinacampeon.R
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.data.previewsource.PreviewPhotos
import com.ferpa.argentinacampeon.domain.model.Player
import com.ferpa.argentinacampeon.domain.model.Tag
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TagInfoBox(
    modifier: Modifier = Modifier,
    tag: Tag?,
    bestPhoto: String? = null,
    photosCount: Int? = null,
    scrollState: State<Float> = mutableStateOf(0.33f)
) {
    if (tag == null) return
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Constants.TOP_BACKGROUND_HEIGHT.dp)
            .background(Constants.VioletDark)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Constants.TOP_BACKGROUND_HEIGHT.dp)
        ) {
            GlideImage(
                model = bestPhoto ?: R.drawable.tag_default_backdrop,
                contentDescription = "tagPhoto",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Constants.TOP_BACKGROUND_HEIGHT.dp)
            )
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
            ) {
                val aspectRatio = maxWidth / maxHeight
                Box(
                    Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth()
                        .scale(maxOf(aspectRatio, 1f), maxOf(1 / aspectRatio, 1f))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xA218182F), Color(0xFFFFFF))
                            ),
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = MaterialTheme.spacing.default,
                    start = MaterialTheme.spacing.medium,
                    bottom = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.medium
                )
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.default),
                    shape = MaterialTheme.shapes.small,
                    backgroundColor = Constants.LightBlue,
                    elevation = 8.dp
                ) {
                    Text(
                        text = "#${tag.tag}",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.spacing.default)
                    )
                }
                Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                    photosCount.apply {
                        Text(
                            text = "Fotos: $this",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(MaterialTheme.spacing.default),
                            textAlign = TextAlign.End,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Constants.VioletDark,
                                    offset = Offset(2.5f, 2.0f),
                                    blurRadius = 2f
                                )
                            )
                        )
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun TagInfoBoxPreview() {
    BestQatar2022PhotosTheme {
        TagInfoBox(tag = Tag(id = "", tag = "Mira que te como", lastUpdate = ""))
    }
}