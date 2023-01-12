package com.ferpa.argentinacampeon.presentation.insertData

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ferpa.argentinacampeon.domain.model.Player
import com.ferpa.argentinacampeon.domain.model.Stats
import com.ferpa.argentinacampeon.presentation.ui.theme.spacing
import java.util.*

@Composable
fun InsertDataScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: InsertDataViewModel = hiltViewModel()
) {
    Box() {
        val displayName = remember { mutableStateOf(TextFieldValue()) }
        val name = remember { mutableStateOf(TextFieldValue()) }
        val nickname = remember { mutableStateOf(TextFieldValue()) }
        val birthday = remember { mutableStateOf(TextFieldValue()) }
        val position = remember { mutableStateOf(TextFieldValue()) }
        val nationalTeam = remember { mutableStateOf(TextFieldValue()) }
        val number = remember { mutableStateOf(TextFieldValue()) }
        val team = remember { mutableStateOf(TextFieldValue()) }
        val matches = remember { mutableStateOf(TextFieldValue()) }
        val minutes = remember { mutableStateOf(TextFieldValue()) }
        val goals = remember { mutableStateOf(TextFieldValue()) }
        val assists = remember { mutableStateOf(TextFieldValue()) }
        val shots = remember { mutableStateOf(TextFieldValue()) }
        val passes = remember { mutableStateOf(TextFieldValue()) }
        val wrongPasses = remember { mutableStateOf(TextFieldValue()) }
        val passAccuracy = remember { mutableStateOf(TextFieldValue()) }
        val recoveries = remember { mutableStateOf(TextFieldValue()) }
        val fouls = remember { mutableStateOf(TextFieldValue()) }
        val foulsReceived = remember { mutableStateOf(TextFieldValue()) }
        val yellowCards = remember { mutableStateOf(TextFieldValue()) }
        val redCards = remember { mutableStateOf(TextFieldValue()) }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spacing.large)
                .align(Alignment.Center)
        ) {
            Button(onClick = { viewModel.insertLocalPlayer(
                Player(
                    id = UUID.randomUUID().toString(),
                    displayName = displayName.value.text,
                    name = name.value.text,
                    nickName = displayName.value.text,
                    birthday = if (!birthday.value.text.isNullOrEmpty()) birthday.value.text else null,
                    position = position.value.text,
                    nationalTeam = nationalTeam.value.text,
                    number = if (!number.value.text.isNullOrEmpty()) number.value.text.toInt() else null,
                    team = team.value.text,
                    stats = Stats(
                        matches = if (!minutes.value.text.isNullOrEmpty()) matches.value.text.toInt() else null,
                        minutes = if (!minutes.value.text.isNullOrEmpty()) minutes.value.text.toInt() else null,
                        goals = if (!goals.value.text.isNullOrEmpty()) goals.value.text.toInt() else null,
                        assists = if (!assists.value.text.isNullOrEmpty()) assists.value.text.toInt() else null,
                        shots = if (!shots.value.text.isNullOrEmpty()) shots.value.text.toInt() else null,
                        passes = if (!passes.value.text.isNullOrEmpty()) passes.value.text.toInt() else null,
                        wrongPasses = if (!wrongPasses.value.text.isNullOrEmpty()) wrongPasses.value.text.toInt() else null,
                        passAccuracy = if (!passAccuracy.value.text.isNullOrEmpty()) passAccuracy.value.text.toInt() else null,
                        recoveries = if (!recoveries.value.text.isNullOrEmpty()) recoveries.value.text.toInt()else null,
                        fouls = if (!fouls.value.text.isNullOrEmpty()) fouls.value.text.toInt() else null,
                        foulsReceived = if (!foulsReceived.value.text.isNullOrEmpty()) foulsReceived.value.text.toInt() else null,
                        yellowCards = if (!yellowCards.value.text.isNullOrEmpty()) yellowCards.value.text.toInt() else null,
                        redCards = if (!redCards.value.text.isNullOrEmpty()) redCards.value.text.toInt() else null,
                    )
                    )
            ) }) {
                Text(text = "Insert Player")
            }
            
            TextField(
                value = displayName.value,
                onValueChange = { displayName.value = it },
                placeholder = { Text(text = "display name") }
            )
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                placeholder = { Text(text = "name") }
            )
            TextField(
                value = nickname.value,
                onValueChange = { nickname.value = it },
                placeholder = { Text(text = "nickname") }
            )
            TextField(
                value = birthday.value,
                onValueChange = { birthday.value = it },
                placeholder = { Text(text = "birthday") },
            )
            TextField(
                value = position.value,
                onValueChange = { position.value = it },
                placeholder = { Text(text = "position") },
            )
            TextField(
                value = nationalTeam.value,
                onValueChange = { nationalTeam.value = it },
                placeholder = { Text(text = "nationalTeam") },
            )
            TextField(
                value = number.value,
                onValueChange = { number.value = it },
                placeholder = { Text(text = "number") }
            )
            TextField(
                value = team.value,
                onValueChange = { team.value = it },
                placeholder = { Text(text = "team") },
            )
            TextField(
                value = matches.value,
                onValueChange = { matches.value = it },
                placeholder = { Text(text = "matches") },
            )
            TextField(
                value = minutes.value,
                onValueChange = { minutes.value = it },
                placeholder = { Text(text = "minutes") },
            )
            TextField(
                value = goals.value,
                onValueChange = { goals.value = it },
                placeholder = { Text(text = "goals") },
            )
            TextField(
                value = assists.value,
                onValueChange = { assists.value = it },
                placeholder = { Text(text = "assists") },
            )
            TextField(
                value = shots.value,
                onValueChange = { shots.value = it },
                placeholder = { Text(text = "shots") },
            )
            TextField(
                value = passes.value,
                onValueChange = { passes.value = it },
                placeholder = { Text(text = "passes") },
            )
            TextField(
                value = wrongPasses.value,
                onValueChange = { wrongPasses.value = it },
                placeholder = { Text(text = "wrongPasses") },
            )
            TextField(
                value = passAccuracy.value,
                onValueChange = { passAccuracy.value = it },
                placeholder = { Text(text = "passAccuracy") },
            )
            TextField(
                value = recoveries.value,
                onValueChange = { recoveries.value = it },
                placeholder = { Text(text = "recoveries") },
            )
            TextField(
                value = fouls.value,
                onValueChange = { fouls.value = it },
                placeholder = { Text(text = "fouls") },
            )
            TextField(
                value = foulsReceived.value,
                onValueChange = { foulsReceived.value = it },
                placeholder = { Text(text = "foulsReceived") },
            )
            TextField(
                value = yellowCards.value,
                onValueChange = { yellowCards.value = it },
                placeholder = { Text(text = "yellowCards") },
            )
            TextField(
                value = redCards.value,
                onValueChange = { redCards.value = it },
                placeholder = { Text(text = "redCards") },
            )

            Box(modifier = Modifier.height(50.dp)) {

            }
            
        }
    }

}