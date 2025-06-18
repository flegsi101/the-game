package de.kugma.the_game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adamglin.composeshadow.dropShadow

private class ScoreShape(val site: Site) : Shape {
    enum class Site {
        Left, Right
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = when (site) {
            Site.Left -> Path().apply {
                lineTo(size.width, 0f)
                lineTo(size.width - (size.height / 2), size.height)
                lineTo(0f, size.height)
                lineTo(0f, 0f)
                close()
            }

            Site.Right -> Path().apply {
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                lineTo(0f + (size.height / 2), 0f)
                close()
            }
        }

        return Outline.Generic(path)
    }

}

private class TitleLine(val height: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            density.apply {
                moveTo(10.dp.toPx(), 0f)
                lineTo(14.dp.toPx(), 0f)
                lineTo(4.dp.toPx(), height.toPx())
                lineTo(0f, height.toPx())
                close()
            }
        }

        return Outline.Generic(path)
    }

}

@Composable
internal fun ScorePanel(
    teamOnePoints: Int,
    teamTwoPoints: Int,
    title: String = ""
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val modifier = Modifier
            .width(200.dp)

        Text(
            text = teamOnePoints.toString(),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(150.dp)
                .dropShadow(
                    shape = ScoreShape(ScoreShape.Site.Left),
                    color = GameColor.Yellow.copy(alpha = 0.5f),
                    blur = 10.dp
                )
                .clip(ScoreShape(ScoreShape.Site.Left))
                .background(GameColor.Yellow)
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 30.dp)
        )

        Box {
            if (title != "")
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(14.dp)
                            .dropShadow(
                                shape = TitleLine(50.dp),
                                color = GameColor.Red,
                                blur = 4.dp,
                                offsetX = 0.dp,
                                offsetY = 0.dp
                            )
                            .background(GameColor.Red, shape = TitleLine(50.dp))
                    ) {}
                    Text(
                        text = title,
                        fontWeight = FontWeight.Black,
                        fontSize = 30.sp,
                        modifier = Modifier
                    )
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(14.dp)
                            .dropShadow(
                                shape = TitleLine(50.dp),
                                color = GameColor.Yellow,
                                blur = 4.dp,
                                offsetX = 0.dp,
                                offsetY = 0.dp
                            )
                            .background(GameColor.Yellow, shape = TitleLine(50.dp))
                    ) {}
                }
        }

        Text(
            text = teamTwoPoints.toString(),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(150.dp)
                .dropShadow(
                    shape = ScoreShape(ScoreShape.Site.Right),
                    color = GameColor.Red.copy(alpha = 0.5f),
                    blur = 10.dp,
                )
                .clip(ScoreShape(ScoreShape.Site.Right))
                .background(GameColor.Red)
                .padding(start = 30.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)

        )
    }
}