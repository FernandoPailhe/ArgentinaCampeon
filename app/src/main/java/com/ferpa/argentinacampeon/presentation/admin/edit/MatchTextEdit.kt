package com.ferpa.argentinacampeon.presentation.admin.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ferpa.argentinacampeon.presentation.ui.theme.BestQatar2022PhotosTheme
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import com.ferpa.argentinacampeon.common.Constants
import com.ferpa.argentinacampeon.domain.model.MatchTitle
import com.ferpa.argentinacampeon.domain.model.getMinimalTitle
import com.ferpa.argentinacampeon.presentation.versus.components.MatchText

@Composable
fun MatchTextEdit(
    matchTitle: MatchTitle?,
    onMatchClick: (MatchTitle) -> Unit = {},
    itemList: Boolean = false,
    matchTitleText: String = ""
) {
    if (matchTitle == null && matchTitleText.isNullOrEmpty()) return
    matchTitle?.let {
        Box(modifier = Modifier.clickable {
            onMatchClick(matchTitle)
        }) {
            Text(
                text = matchTitle.getMinimalTitle() ,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.default,
                        vertical = MaterialTheme.spacing.extraSmall)
                    .fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatchTextPreview() {
    BestQatar2022PhotosTheme {
        MatchText(
            MatchTitle(
                id = "",
                title = "Cuartos de final: Paises Bajos vs. Argentina",
                score = "2 - 2 (3 - 4)"
            )
        )
    }
}