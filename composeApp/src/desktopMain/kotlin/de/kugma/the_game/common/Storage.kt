package de.kugma.the_game.common

import de.kugma.the_game.GameLog
import java.io.File

class Storage {
    companion object {
        private val appDir: File = File("${System.getProperty("user.home")}/.theGame")

        init {
            appDir.mkdirs()
        }

        fun file(name: String): File {
            val file = appDir.resolve(name)
            if (!file.exists())
                file.createNewFile()
            return file
        }
    }
}