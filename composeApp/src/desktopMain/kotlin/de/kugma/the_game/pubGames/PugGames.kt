package de.kugma.the_game.pubGames

import de.kugma.the_game.common.Team
import de.kugma.the_game.common.createPersistedState
import de.kugma.the_game.common.update
import kotlinx.serialization.Serializable

internal enum class Round {
    None, Bierdeckflippen, Tresenschupsen, Wuerfeldart, Ausstechen
}

@Serializable
internal data class State(
    var currentGame: Round = Round.None,
    var team: MutableMap<Round, Team> = mutableMapOf(),
    var wuerfeldartPoints: MutableMap<Team, Int> = mutableMapOf(
        Pair(Team.Team1, 64),
        Pair(Team.Team2, 64)
    ),
    var tresenschupsenPoints: MutableMap<Team, Int> = mutableMapOf(
        Pair(Team.Team1, 0),
        Pair(Team.Team2, 0)
    ),
    var ausstechenWinner: MutableList<Team> = mutableListOf(Team.None, Team.None, Team.None)
)

class PubGames {

    private val _state = createPersistedState("pubGames.json", State())
    internal val state = _state.asStateFlow()

    internal fun onGameSelect(game: Round) {
        _state.update {
            it.copy(
                currentGame = game
            )
        }
    }

    internal fun setWinner(game: Round, team: Team) {
        _state.update { current ->
            val winnerMap = current.team.toMutableMap()
            winnerMap[game] = team
            current.copy(
                team = winnerMap
            )
        }
    }

    fun setWuerfeldartPoints(team: Team, points: Int) {
        _state.update { current ->
            val wuerfeldartPoints = current.wuerfeldartPoints.toMutableMap()
            wuerfeldartPoints[team] = wuerfeldartPoints[team]!! - points
            if (wuerfeldartPoints[team]!! < 0)
                wuerfeldartPoints[team] = current.wuerfeldartPoints[team]!!
            current.copy(
                wuerfeldartPoints = wuerfeldartPoints
            )
        }
    }

    fun setTresenschupsenPoints(team: Team, points: Int) {
        _state.update { current ->
            val pointsMap = current.tresenschupsenPoints.toMutableMap()
            pointsMap[team] = pointsMap[team]!! + points
            current.copy(
                tresenschupsenPoints = pointsMap
            )
        }
    }

    fun setAusstechenWinner(round: Int, team: Team) {
        _state.update { current ->
            val winnerList = current.ausstechenWinner.toMutableList()
            winnerList[round] = team
            current.copy(
                ausstechenWinner = winnerList
            )
        }
    }
}