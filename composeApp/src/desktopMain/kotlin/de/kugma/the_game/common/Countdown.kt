package de.kugma.the_game.common

import java.util.Timer
import java.util.TimerTask

class Countdown(private val initial: Int, private val onChange: (seconds: Int) -> Unit) {
    private var remaining = initial

    private var timer: Timer? = null

    private fun countdownTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                remaining -= 1
                if (remaining <= 0)
                    stop()
                onChange(remaining)
            }
        }
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
        onChange(remaining)
    }
}