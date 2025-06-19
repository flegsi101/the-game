package de.kugma.the_game.jeopardy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

interface JeopardyQuestion {
    fun getPoints(): Int

    @Composable
    fun renderModeration()

    @Composable
    fun renderPresentation()
}

@Composable
fun SimpleQuestion(question: String) {
    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text(
            text = question,
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun MutlilineQuestion(questions: List<Pair<String, TextUnit>>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        for (question in questions) {
            Text(
                text = question.first,
                textAlign = TextAlign.Center,
                fontSize = question.second,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

//==========================================================
// Baderegeln
//==========================================================

class Baderegeln100 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 100
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Was mache ich vor dem Schwimmen?") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Ich kühle mich ab")
    }
}

class Baderegeln200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Was darf ich nicht machen?") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Rennen, Schubsen, unter Wasser drücken")

    }
}

class Baderegeln300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Joker") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("JOKER 🥳")
    }
}

class Baderegeln400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Was mache ich bei Gewitter?") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Ich verlasse sofort das Wasser")
    }
}

class Baderegeln500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Warum darf man sich nicht auf Schwimmflügel oder Luftmatratzen verlassen?") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Sie sind nicht sicher und schützen nicht vor dem Ertrinken")
    }
}

//==========================================================
// Woher soll ich das Wissen?
//==========================================================

class Wissen100 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 100
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Nase und Ohren") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Welcher Körperteil wächst ein Leben lang weiter?")
    }
}

class Wissen200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Wegen der Farbstoffe in ihrer Nahrung (z.B. Garnelen)") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Warum sehen Flamingos pink aus?")

    }
}

class Wissen300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun renderModeration() {
        Column { Text("3") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Wie viele Herzen hat ein Tintenfisch?")
    }
}

class Wissen400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun renderModeration() {
        Column { Text("etwa 5 - 6 Liter (ca. 8% Körpergewicht)") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Wie viele Liter Blut hat ein durchschnittlicher Mensch?")
    }
}

class Wissen500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Graphen") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Welcher Stoff ist härter als Diamant – und kommt sogar in Bleistiften vor?")
    }
}

//==========================================================
// Emoji
//==========================================================
@Composable
private fun EmojiPresentation(question: String, emojis: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Text(
            text = question,
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = emojis,
            textAlign = TextAlign.Center,
            letterSpacing = TextUnit(40f, TextUnitType.Sp),
            fontSize = 120.sp
        )
    }
}

class Emoji100 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 100
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Batman") }
    }

    @Composable
    override fun renderPresentation() {
        EmojiPresentation("Welcher Film wird gesucht?", "🤵‍♂️🦇🌃")
    }
}

class Emoji200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Batman") }
    }

    @Composable
    override fun renderPresentation() {
        EmojiPresentation("Welches Spiel wird gesucht?", "⛏️🟫🟩🏡")
    }
}

class Emoji300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Auch ein blindes Huhn findet mal ein Korn") }
    }

    @Composable
    override fun renderPresentation() {
        EmojiPresentation("Welches Sprichwort wird gesucht?", "🦯🐓🔎🌽")
    }
}

class Emoji400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Sonic") }
    }

    @Composable
    override fun renderPresentation() {
        EmojiPresentation("Welcher Film wird gesucht?", "🦔🔵💨👟")
    }
}

class Emoji500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Ich glaube mein Schwein pfeift") }
    }

    @Composable
    override fun renderPresentation() {
        EmojiPresentation("Welches Sprichwort wird gesucht?", "🤔🐷😗🎵")
    }
}

//==========================================================
// Wortkunst
//==========================================================

class Wortkunst100 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 100
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Marke") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Was ergibt das Anagramm „ramke“?")
    }
}

class Wortkunst200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Im Regen stehen lassen") }
    }

    @Composable
    override fun renderPresentation() {
        MutlilineQuestion(
            listOf(
                Pair("Welches Sprichwort ist hier verdreht?", 50.sp),
                Pair("„Im Regen sitzen“", 60.sp),
            )
        )
    }
}

class Wortkunst300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Bank - Sitzgelegenheit, Geldinstitut, Sandbank") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Welches deutsche Wort hat 3 Bedeutungen?")
    }
}

class Wortkunst400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Tat") }
    }

    @Composable
    override fun renderPresentation() {
        SimpleQuestion("Welches Wort steckt in: „TOMATE“ + „RATTE“ – aber nicht in „MOTTE“?")
    }
}

class Wortkunst500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun renderModeration() {
        Column { Text("Der Mensch - Kind krabbeln, Erwachsener normal, Alter mit Stock") }
    }

    @Composable
    override fun renderPresentation() {
        MutlilineQuestion(
            listOf(
                Pair("Das Rätsel der Sphinx", 50.sp),
                Pair(
                    "Was geht am Morgen auf vier, am Mittag auf zwei und am Abend auf drei Beinen?",
                    60.sp
                ),
            )
        )
    }
}

//==========================================================
// Fake or Real?
//==========================================================
@Composable
fun FakeOrRealPresentation(question: String, a: String, b: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Text(
            text = question,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(
                text = a,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = b,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

class FakeOrReal100 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 100
    }

    @Composable
    override fun renderModeration() {
        Column { Text("A ist richtig (Metall dehnt sich aus)") }
    }

    @Composable
    override fun renderPresentation() {
        FakeOrRealPresentation(
            "Was ist richtig?",
            "A) Der Eiffelturm ist im Sommer höher als im Winter.",
            "B) Der Mond ist größer als die Erde."
        )
    }
}

class FakeOrReal200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun renderModeration() {
        Column { Text("B ist richtig (z.B. durch Ring schwimmen)") }
    }

    @Composable
    override fun renderPresentation() {
        FakeOrRealPresentation(
            "Was ist richtig?",
            "A) Goldfische haben ein 3-Sekunden-Gedächtnis.",
            "B) Goldfische können trainiert werden.",
        )
    }
}

class FakeOrReal300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun renderModeration() {
        Column { Text("A ist richtig (einige tausend Jahre her)") }
    }

    @Composable
    override fun renderPresentation() {
        FakeOrRealPresentation(
            "Was ist richtig?",
            "A) Die Sahara war einmal ein grüner Urwald.",
            "B) Es hat noch nie in Ägypten geschneit.",
        )
    }
}

class FakeOrReal400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun renderModeration() {
        Column { Text("A ist richtig (es gibt Poststationen für Touristen)") }
    }

    @Composable
    override fun renderPresentation() {
        FakeOrRealPresentation(
            "Was ist richtig?",
            "A) In der Antarktis gibt es eine Poststation.",
            "B) In Island gibt es keine Insekten.",
        )
    }
}

class FakeOrReal500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun renderModeration() {
        Column { Text("B ist richtig (ein untersuchtes Exemplar geschätzt Alter von 392±120 Jahren)") }
    }

    @Composable
    override fun renderPresentation() {
        FakeOrRealPresentation(
            "Was ist richtig?",
            "A) Menschen und Dinosaurier lebten zur gleichen Zeit.",
            "B) Einige Haie sind älter als Bäume.",
        )
    }
}
