package de.kugma.the_game.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun AutoResizeText(
    text: String,
    style: TextStyle = MaterialTheme.typography.body1,
    modifier: Modifier = Modifier,
    color: Color = style.color,
) {
    var resizedTextStyle by remember { mutableStateOf(style) }

    Text(
        text = text,
        color = color,
        modifier = modifier,
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
                println("Resized: ${resizedTextStyle.fontSize}")
            }
        }
    )
}