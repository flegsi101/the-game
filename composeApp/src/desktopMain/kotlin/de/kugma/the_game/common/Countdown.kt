package de.kugma.the_game.common

import thegame.composeapp.generated.resources.Res
import java.io.File
import java.net.URI
import java.util.Timer
import java.util.TimerTask
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

class Countdown(private val initial: Int, private val onChange: (seconds: Int) -> Unit) {
    private var remaining = initial

    private var timer: Timer? = null
    private var notificationClip: Clip = loadNotification()

    private fun countdownTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                remaining -= 1
                if (remaining <= 0) {
                    stop()
                    notificationClip.start()
                }
                onChange(remaining)
            }
        }
    }

    private fun loadNotification(): Clip {
        val audioStream = AudioSystem.getAudioInputStream(File(URI(Res.getUri("files/gong.wav"))))
        val clip = AudioSystem.getClip()
        clip.open(audioStream)
        return clip
    }

    fun start() {
        timer = Timer()
        timer?.schedule(countdownTask(), 1000L, 1000L)
    }

    fun stop() {
        try {
            timer?.cancel()
            timer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun reset(seconds: Int = initial) {
        stop()
        remaining = seconds
        loadNotification()
        onChange(remaining)
    }
}