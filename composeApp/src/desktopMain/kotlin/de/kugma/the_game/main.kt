package de.kugma.the_game

import androidx.compose.ui.window.application

fun main() = application {
    val theGame = TheGame(onExit = ::exitApplication)
    theGame.ModerationWindow()
    theGame.PresentationWindow()
}