package de.kugma.the_game.querBeet

import androidx.compose.runtime.Composable

@Composable
internal fun SchuettenModeration(game: QuerBeetGame) {
    WinnerSelector(game, QuerBeetRound.Schuetten)
}