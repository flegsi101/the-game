package de.kugma.the_game.jeopardy

import androidx.compose.runtime.mutableStateListOf
import de.kugma.the_game.common.Countdown
import de.kugma.the_game.common.createPersistedState
import de.kugma.the_game.common.update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

@Serializable
internal data class State(
    var pointsTeamOne: Int = 0,
    var pointsTeamTwo: Int = 0,
    var openQuestion: Pair<Int, Int>? = null,
    var questionStates: MutableList<MutableList<Boolean>> = mutableStateListOf(
        mutableStateListOf(false, false, false, false, false),
        mutableStateListOf(false, false, false, false, false),
        mutableStateListOf(false, false, false, false, false),
        mutableStateListOf(false, false, false, false, false),
        mutableStateListOf(false, false, false, false, false),
    ),
    @Transient val countdownSeconds: Int = 0,
    @Transient val countdownRunning: Boolean = false
)

class Jeopardy {

    companion object {
        val Categories = listOf(
            "Baderegeln",
            "Woher soll ich das Wissen?",
            "Emoji",
            "Wortkunst",
            "Fake or Real?"
        )
    }

    private val _state = createPersistedState("jeopardy.json", State())
    internal val state = _state.asStateFlow()
    internal val currentQuestion: Flow<JeopardyQuestion?> = state.map {
        if (it.openQuestion == null) null else mapQuestion(
            it.openQuestion!!.first,
            it.openQuestion!!.second
        )
    }

    val countdown = Countdown(10) { remaining ->
        _state.update { it.copy(countdownSeconds = remaining) }
    }

    private val _onTitle = MutableStateFlow(true)
    internal val onTitle = _onTitle.asStateFlow()

    private fun mapQuestion(category: Int, points: Int): JeopardyQuestion? {
        return when (category) {
            0 -> when (points) {
                0 -> Baderegeln100()
                1 -> Baderegeln200()
                2 -> Baderegeln300()
                3 -> Baderegeln400()
                4 -> Baderegeln500()
                else -> null
            }

            1 -> when (points) {
                0 -> Wissen100()
                1 -> Wissen200()
                2 -> Wissen300()
                3 -> Wissen400()
                4 -> Wissen500()
                else -> null
            }

            2 -> when (points) {
                0 -> Emoji100()
                1 -> Emoji200()
                2 -> Emoji300()
                3 -> Emoji400()
                4 -> Emoji500()
                else -> null
            }

            3 -> when (points) {
                0 -> Wortkunst100()
                1 -> Wortkunst200()
                2 -> Wortkunst300()
                3 -> Wortkunst400()
                4 -> Wortkunst500()
                else -> null
            }

            4 -> when (points) {
                0 -> FakeOrReal100()
                1 -> FakeOrReal200()
                2 -> FakeOrReal300()
                3 -> FakeOrReal400()
                4 -> FakeOrReal500()
                else -> null
            }

            else -> null
        }
    }

    fun reset() {
        removeCountdown()
        _state.update { State() }
    }

    fun closeQuestion(mark: Boolean = true) {
        removeCountdown()
        _state.update { current ->
            {}
            val questionStates = current.questionStates
            questionStates[current.openQuestion!!.first][current.openQuestion!!.second] =
                questionStates[current.openQuestion!!.first][current.openQuestion!!.second] || mark

            current.copy(
                openQuestion = null,
                questionStates = questionStates
            )
        }
    }

    fun givePointsTeamOne(points: Int) {
        _state.update {
            it.copy(
                pointsTeamOne = it.pointsTeamOne + points
            )
        }
    }

    fun givePointsTeamTwo(points: Int) {
        _state.update {
            it.copy(
                pointsTeamTwo = it.pointsTeamTwo + points
            )
        }
    }

    fun start() {
        _onTitle.update { false }
    }

    fun openQuestion(category: Int, points: Int) {
        _state.update {
            it.copy(
                openQuestion = Pair(category, points)
            )
        }
    }

    fun startCountdown() {
        _state.update { it.copy(countdownRunning = true) }
        countdown.reset(10)
        countdown.start()
    }

    fun removeCountdown() {
        _state.update { it.copy(countdownRunning = false) }
        countdown.stop()
    }
}