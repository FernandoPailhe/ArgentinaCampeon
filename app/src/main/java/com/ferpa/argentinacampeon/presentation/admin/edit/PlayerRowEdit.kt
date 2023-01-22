package com.ferpa.argentinacampeon.presentation.admin.edit

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ferpa.argentinacampeon.domain.model.Player
import com.ferpa.argentinacampeon.domain.model.PlayerTitle
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.versus.components.PlayerCard
import com.ferpa.argentinacampeon.presentation.versus.components.PlayerRow


@Composable
fun PlayerRowEdit(
    modifier: Modifier = Modifier,
    players: List<PlayerTitle?>?,
    onPlayerClick: (PlayerTitle) -> Unit = {}
) {

    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState, true),
        horizontalArrangement = Arrangement.Center
    ) {
        players?.forEach {
            it?.let { PlayerCardEdit(player = it, onPlayerClick) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerRowPreview() {
    BestQatar2022PhotosTheme {
        PlayerRow(
            players = listOf(
                PlayerTitle(id = "", displayName = "Lionel Messi"),
                PlayerTitle(id = "", displayName = "Enzo Fernandez"),
            )
        )
    }
}