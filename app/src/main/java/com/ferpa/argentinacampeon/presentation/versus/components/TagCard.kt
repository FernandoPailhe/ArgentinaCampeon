package com.ferpa.argentinacampeon.presentation.versus.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.common.Constants.NO_TAG_KEY
import com.ferpa.argentinacampeon.domain.model.Tag

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TagCard(tag: Tag, onTagClick: (String) -> Unit = {}, isCard: Boolean = true) {

    Box(
        modifier = Modifier.fillMaxWidth().clickable {
            onTagClick(tag.id)
        }
    ) {
        if (isCard && tag.tag != NO_TAG_KEY) {
            Card(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.default),
                shape = shapes.small,
                backgroundColor = Constants.LightBlue,
                elevation = 8.dp
            ) {
                Text(
                    text = "#${tag.tag}",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.default)
                )
            }
        } else {
            Text(
                text = if (tag.tag == NO_TAG_KEY) "" else "#${tag.tag}",
                fontWeight = FontWeight.SemiBold,
                color = Constants.VioletDark,
                fontSize = 10.sp,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.default,
                        vertical = MaterialTheme.spacing.extraSmall)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagCardPreview() {
    BestQatar2022PhotosTheme {
        TagCard(Tag("tag ", "epic"))
    }
}