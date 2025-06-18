package de.kugma.the_game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Game {
    @Composable
    fun presentation(modifier: Modifier = Modifier)

    @Composable
    fun moderation(modifier: Modifier = Modifier)
}