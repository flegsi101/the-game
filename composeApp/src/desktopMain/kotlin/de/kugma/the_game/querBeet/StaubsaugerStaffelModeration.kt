package de.kugma.the_game.querBeet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
internal fun StaubsaugerStaffelModeration(game: QuerBeetGame) {
    WinnerSelector(game, QuerBeetRound.StaubsaugerStaffel)
}