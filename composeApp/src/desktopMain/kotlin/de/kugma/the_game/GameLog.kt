package de.kugma.the_game

import de.kugma.the_game.common.Storage
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GameLog {
    companion object {
        private val logFile: File = Storage.file("log.txt")

        init {
            logFile.appendText("===== START=====\n")
        }

        val timestampFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        fun logToFile(text: String) {
            val now = timestampFormat.format(LocalDateTime.now())
            logFile.appendText("[$now] $text\n")
        }
    }
}