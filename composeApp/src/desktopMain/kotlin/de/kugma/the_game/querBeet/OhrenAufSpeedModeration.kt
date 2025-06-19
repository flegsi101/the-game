package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioSystem


@Composable
internal fun OhrenAufSpeedModeration(querBeet: QuerBeetGame) {
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
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam1 = it.pointsTeam1 + 5) } }) {
                        Text("5 Pkt")
                    }
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam1 = it.pointsTeam1 + 3) } }) {
                        Text("3 Pkt")
                    }
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam1 = it.pointsTeam1 + 1) } }) {
                        Text("1 Pkt")
                    }
                }
            }

            Column {
                Text("Team 2")
                Row {
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam2 = it.pointsTeam2 + 5) } }) {
                        Text("5 Pkt")
                    }
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam2 = it.pointsTeam2 + 3) } }) {
                        Text("3 Pkt")
                    }
                    Button(onClick = { querBeet.updateOhrenAufSpeed { it -> it.copy(pointsTeam2 = it.pointsTeam2 + 1) } }) {
                        Text("1 Pkt")
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
