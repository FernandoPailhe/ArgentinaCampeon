package com.ferpa.argentinacampeon.presentation.versus.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ferpa.argentinacampeon.domain.model.Tag
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme

@Composable
fun TagRow(
    tags: List<Tag?>?,
    onTagClick: (String) -> Unit = {},
    isCard: Boolean = true
) {

    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState, true),
        horizontalArrangement = Arrangement.Center
    ) {
        tags?.forEach {
            it?.let { TagCard(tag = it, onTagClick, isCard = isCard) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagRowPreview() {
    BestQatar2022PhotosTheme {
        TagRow(
            tags = listOf(
                Tag("1","epic"),
                Tag("1","gol"),
                Tag("1","festejo"),
                Tag("1","cuartos de final"),
                Tag("1","Scaloneta"),
                Tag("1","Atajadón")
            )
        )
    }
}