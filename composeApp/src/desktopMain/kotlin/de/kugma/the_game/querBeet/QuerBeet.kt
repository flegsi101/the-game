package de.kugma.the_game.querBeet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.kugma.the_game.TheGame
import de.kugma.the_game.common.Team
import de.kugma.the_game.common.createPersistedState
import de.kugma.the_game.common.update
import kotlinx.coroutines.flow.map

class QuerBeetGame(val conext: TheGame) {

    private val _state = createPersistedState("querBeet.json", QuerBeetState())
    internal val state = _state.asStateFlow()

    internal val ohrenAufSpeedState = state.map { it.ohrenAufSpeedState }

    internal val kuenstlerDuettViewModel = KuensterDuettViewModel(this)

    internal fun setRound(round: QuerBeetRound) {
        _state.update { it.copy(currentRound = round) }
    }

    internal fun updateOhrenAufSpeed(function: (current: OhrenAufSpeedState) -> OhrenAufSpeedState) {
        _state.update {
            it.copy(
                ohrenAufSpeedState = function(it.ohrenAufSpeedState)
            )
        }
    }

    internal fun onRoundWon(round: QuerBeetRound, team: Team) {
        val winner = state.value.winner.toMutableMap()
        winner[round] = team
        _state.update { it.copy(winner = winner, currentRound = QuerBeetRound.None) }
    }

    internal fun resetState() {
        _state.update { QuerBeetState() }
    }
}