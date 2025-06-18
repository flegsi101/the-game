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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioSystem


@Composable
fun QuerBeetModeration(game: QuerBeetGame, modifier: Modifier = Modifier) {
    val state by game.state.collectAsState()

    Column(modifier) {
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
            QuerBeetRound.OhrenAufSpeed -> OhrenAufSpeed(game)
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

    Button(
        onClick = { querBeet.setRound(round) },
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonBackground),
        modifier = Modifier.weight(1f)
    ) {
        Text(round.title)
    }
}

@Composable
private fun OhrenAufSpeed(querBeet: QuerBeetGame) {
    val coroutineScope = rememberCoroutineScope()
    val songBoard = remember { SongBoard(coroutineScope) }

    val ohrenAufSpeedState by querBeet.ohrenAufSpeedState.collectAsState(OhrenAufSpeedState())
    var currentSong by remember { mutableStateOf(OhrenAufSpeedSong.APT) }

    fun setCurrentSong(song: OhrenAufSpeedSong) {
        currentSong = song
    }



    Column {
        WinnerSelector(querBeet, QuerBeetRound.OhrenAufSpeed)

        Row {
            Button(onClick = { querBeet.updateOhrenAufSpeed { OhrenAufSpeedState() } }) { Text("reset") }
            Button(onClick = { songBoard.stop() }) { Text("stop") }
        }

        FlowRow {
            SongButton(OhrenAufSpeedSong.APT, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.bibiBlocksberg, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.asItWas, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.baller, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.dancingQueen, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.emptinessMachine, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.fliegerlied, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.komet, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.wildberryLillet, ::setCurrentSong)
            SongButton(OhrenAufSpeedSong.tageWieDiese, ::setCurrentSong)
        }

        Row {
            Text(currentSong.title)
            Button(onClick = { songBoard.play(currentSong.oneSecond) }) { Text("1s") }
            Button(onClick = { songBoard.play(currentSong.threeSeconds) }) { Text("3s") }
            Button(onClick = { songBoard.play(currentSong.fiveSeconds) }) { Text("5s") }
            Button(onClick = { songBoard.play(currentSong.solution) }) { Text("solution") }
        }

        Row {
            Column {
                Text("Team 1")
                Row {
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam1 = it.pointsTeam1 + 1) } }) {
                        Text("1 Pkt")
                    }
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam1 = it.pointsTeam1 + 3) } }) {
                        Text("3 Pkt")
                    }
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam1 = it.pointsTeam1 + 5) } }) {
                        Text("5 Pkt")
                    }
                }
            }

            Column {
                Text("Team 2")
                Row {
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam2 = it.pointsTeam2 + 1) } }) {
                        Text("1 Pkt")
                    }
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam2 = it.pointsTeam2 + 3) } }) {
                        Text("3 Pkt")
                    }
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam2 = it.pointsTeam2 + 5) } }) {
                        Text("5 Pkt")
                    }
                }

            }
        }
    }
}

@Composable
private fun SongButton(song: OhrenAufSpeedSong, onSelect: (song: OhrenAufSpeedSong) -> Unit) {
    Button(onClick = { onSelect(song) }) {
        Text(song.title)
    }
}

class SongBoard(val coroutineScope: CoroutineScope) {

    private val clip = AudioSystem.getClip()

    fun play(snippet: ByteArray) {
        coroutineScope.launch {
            if (clip.isActive)
                clip.stop()
            clip.close()
            val inputStream = AudioSystem.getAudioInputStream(ByteArrayInputStream(snippet))
            clip.open(inputStream)
            clip.start()
        }
    }

    fun stop() {
        coroutineScope.launch {
            clip.stop()
            clip.close()
        }
    }
}