package com.ferpa.argentinacampeon.presentation.versus.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.domain.model.PhotographerTitle
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing

@Composable
fun AuthorBox(
    modifier: Modifier = Modifier,
    author: PhotographerTitle,
    onPhotographerClick: () -> Unit = {}
) {
    if (author.name.isNullOrEmpty()) return
    Text(
        text = "Ph: ${author.name}",
        fontWeight = FontWeight.SemiBold,
        color = Constants.VioletDark,
        fontSize = 10.sp,
        modifier = Modifier
            .padding(MaterialTheme.spacing.extraSmall).blur(1.dp).offset(2.dp, 2.dp)
    )
    Text(
        text = "Ph: ${author.name}",
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        fontSize = 10.sp,
        modifier = Modifier
            .padding(MaterialTheme.spacing.extraSmall)
    )
}

@Preview(showBackground = true)
@Composable
fun AuthorBoxPreview() {
    BestQatar2022PhotosTheme {
        AuthorBox(author = PhotographerTitle(id = "", name = "Josefa Fotografa"))
    }
}