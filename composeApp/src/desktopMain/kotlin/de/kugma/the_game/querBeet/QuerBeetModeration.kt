package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.kugma.the_game.common.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioSystem


@Composable
fun QuerBeetModeration(game: QuerBeetGame, modifier: Modifier = Modifier) {
    val state by game.state.collectAsState()

    Column(modifier) {
        Row {
            Button(onClick = { game.resetState() }) { Text("reset QuerBeet") }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            RoundTrigger(game, QuerBeetRound.None)
            RoundTrigger(game, QuerBeetRound.StaubsaugerStaffel)
            RoundTrigger(game, QuerBeetRound.KuenstlerDuett)
            RoundTrigger(game, QuerBeetRound.Schuetten)
            RoundTrigger(game, QuerBeetRound.OhrenAufSpeed)
        }

        when (state.currentRound) {
            QuerBeetRound.None -> {}
            QuerBeetRound.StaubsaugerStaffel -> StaubsaugerStaffelModeration(game)
            QuerBeetRound.KuenstlerDuett -> KuenstlerDuettModeration(game)
            QuerBeetRound.Schuetten -> SchuettenModeration(game)
            QuerBeetRound.OhrenAufSpeed -> OhrenAufSpeedModeration(game)
        }
    }
}

@Composable
private fun RowScope.RoundTrigger(querBeet: QuerBeetGame, round: QuerBeetRound) {
    val state by querBeet.state.collectAsState()


    val bgNormal = MaterialTheme.colors.primary
    val bgActive = MaterialTheme.colors.secondary
    val buttonBackground by derivedStateOf {
        if (state.currentRound == round) bgActive else bgNormal
    }

    Column(modifier = Modifier.weight(1f)) {
        Button(
            onClick = { querBeet.setRound(round) },
            colors = ButtonDefaults.buttonColors(backgroundColor = buttonBackground),
        ) {
            Text(round.title)
        }
        if (round != QuerBeetRound.None)
            Button(onClick = {querBeet.onRoundWon(round, Team.None)}) { Text("reset winner") }
    }
}