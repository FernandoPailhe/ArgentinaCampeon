package com.ferpa.argentinacampeon.presentation.admin.edit

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferpa.argentinacampeon.data.previewsource.PreviewPhotos
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.domain.model.PhotographerTitle
import com.ferpa.argentinacampeon.domain.model.PlayerTitle
import com.ferpa.argentinacampeon.presentation.versus.components.PlayerCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhCardEdit(photographerTitle: PhotographerTitle, onPhotographerClick: (PhotographerTitle) -> Unit = {}) {
    photographerTitle.name?.let {
        Card(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.extraSmall
                ),
            shape = shapes.small,
            elevation = 8.dp,
            backgroundColor = Constants.Violet,
            onClick = { onPhotographerClick(photographerTitle) }
        ) {
            Text(
                text = photographerTitle.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.default)
            )
        }
    }
}
