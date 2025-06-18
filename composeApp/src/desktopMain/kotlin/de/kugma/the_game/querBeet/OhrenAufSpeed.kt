package de.kugma.the_game.querBeet

import kotlinx.serialization.Serializable
import thegame.composeapp.generated.resources.Res
import java.io.File
import java.net.URI

@Serializable
internal data class OhrenAufSpeedState(
    val pointsTeam1: Int = 0,
    val pointsTeam2: Int = 0
)

internal class OhrenAufSpeedSong(filePrefix: String, val title: String) {

    companion object {
        val APT: OhrenAufSpeedSong = OhrenAufSpeedSong("apt", "A.P.T")
        val asItWas: OhrenAufSpeedSong = OhrenAufSpeedSong("as_it_was", "As it was")
        val baller: OhrenAufSpeedSong = OhrenAufSpeedSong("baller", "Baller")
        val bibiBlocksberg: OhrenAufSpeedSong = OhrenAufSpeedSong("bibi_blocksberg", "Bibi Blocksberg")
        val dancingQueen: OhrenAufSpeedSong = OhrenAufSpeedSong("dancing_queen", "Dancing Queen")
        val emptinessMachine: OhrenAufSpeedSong = OhrenAufSpeedSong("emptiness_machine", "Emptiness Machine")
        val fliegerlied: OhrenAufSpeedSong = OhrenAufSpeedSong("fliegerlied", "Fliegerlied")
        val komet: OhrenAufSpeedSong = OhrenAufSpeedSong("komet", "Komet")
        val tageWieDiese: OhrenAufSpeedSong = OhrenAufSpeedSong("tage_wie_diese", "Tage wie diese")
        val wildberryLillet: OhrenAufSpeedSong = OhrenAufSpeedSong("wildberry_lillet", "Wildberry Lillet")
    }

    val oneSecond: ByteArray
    val threeSeconds: ByteArray
    val fiveSeconds: ByteArray
    val solution: ByteArray

    init {
        oneSecond = loadSnippet("files/${filePrefix}_1s.wav")
        threeSeconds = loadSnippet("files/${filePrefix}_3s.wav")
        fiveSeconds = loadSnippet("files/${filePrefix}_5s.wav")
        solution = loadSnippet("files/${filePrefix}_solution.wav")
    }

    private fun loadSnippet(resource: String): ByteArray {
        return File(URI(Res.getUri(resource))).absoluteFile.readBytes()
    }
}