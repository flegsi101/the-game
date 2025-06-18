package de.kugma.the_game.querBeet

import de.kugma.the_game.common.createPersistedState
import de.kugma.the_game.common.update
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

internal enum class Round(val title: String) {
    None("Home"),
    StaubsaugerStaffel("Staubsauger Staffel"),
    OhrenAufSpeed("Ohren auf Speed"),
    Schuetten("Schütten"),
    KuenstlerDuett("Künstler Duett"),
}

@Serializable
internal data class State(
    val currentRound: Round = Round.None,
    val ohrenAufSpeedState: OhrenAufSpeedState = OhrenAufSpeedState(),
    val kuenstlerDuettState: KuenstlerDuettState = KuenstlerDuettState()
)

class QuerBeet {
    private val _querBeetState = createPersistedState("querBeet.json", State())
    internal val querBeetState = _querBeetState.asStateFlow()
    internal val ohrenAufSpeedState = querBeetState.map { it.ohrenAufSpeedState }
    internal val kuenstlerDuettState = querBeetState.map { it.kuenstlerDuettState }

    internal fun setRound(round: Round) {
        _querBeetState.update { it.copy(currentRound = round) }
    }

    internal fun updateOhrenAufSpeed(function: (current: OhrenAufSpeedState) -> OhrenAufSpeedState) {
        _querBeetState.update {
            it.copy(
                ohrenAufSpeedState = function(it.ohrenAufSpeedState)
            )
        }
    }

    internal fun resetKuenstlerDuett() {
        _querBeetState.update {
            it.copy(
                kuenstlerDuettState = KuenstlerDuettState()
            )
        }
    }

    internal fun initKuenstlerDuett() {
        _querBeetState.update {
            it.copy(
                kuenstlerDuettState = initKuenstlerDuettRounds(it.kuenstlerDuettState)
            )
        }
    }

    internal fun updateKuenstlerDuett(function: (current: KuenstlerDuettState) -> KuenstlerDuettState) {
        _querBeetState.update {
            it.copy(
                kuenstlerDuettState = function(it.kuenstlerDuettState)
            )
        }
    }
}