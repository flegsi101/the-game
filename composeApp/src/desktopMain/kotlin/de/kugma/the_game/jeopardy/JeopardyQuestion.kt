package de.kugma.the_game.jeopardy

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import de.kugma.the_game.GameWindow

interface JeopardyQuestion {
    fun getPoints(): Int

    @Composable
    fun render(window: GameWindow)
}

//==========================================================
// Baderegeln
//==========================================================
class Baderegeln100 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 100
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Was mache ich vor dem Schwimmen?")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Ich kühle mich ab")
            }
        }
    }
}

class Baderegeln200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Was darf ich nicht machen?")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Rennen, Schubsen, unter Wasser drücken")
            }
        }
    }
}

class Baderegeln300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Joker")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("JOKER 🥳")
            }
        }
    }
}

class Baderegeln400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Was mache ich bei Gewitter?")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Ich verlasse sofort das Wasser")
            }
        }
    }
}

class Baderegeln500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Warum darf man sich nicht auf Schwimmflügel oder Luftmatratzen verlassen?")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Sie sind nicht sicher und schützen nicht vor dem Ertrinken")
            }
        }
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
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Nase und Ohren")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welcher Körperteil wächst ein Leben lang weiter?")
            }
        }
    }
}

class Wissen200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Wegen der Farbstoffe in ihrer Nahrung (z.B. Garnelen)")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Warum sehen Flamingos pink aus?")
            }
        }
    }
}

class Wissen300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("3")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Wie viele Herzen hat ein Tintenfisch?")
            }
        }
    }
}

class Wissen400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("etwa 5 - 6 Liter (ca. 8% Körpergewicht)")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Wie viele Liter Blut hat ein durchschnittlicher Mensch")
            }
        }
    }
}

class Wissen500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Graphen")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welcher Stoff ist härter als Diamant – und kommt sogar in Bleistiften vor?")
            }
        }
    }
}

//==========================================================
// Emoji
//==========================================================

class Emoji100 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 100
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Batman")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welcher Film wird gesucht?")
                Text("🤵‍♂️🦇🌃")
            }
        }
    }
}

class Emoji200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Batman")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welches Spiel wird gesucht?")
                Text("⛏️🟫🟩🏡")
            }
        }
    }
}

class Emoji300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Auch ein blindes Huhn findet mal ein Korn")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welches Sprichwort wird gesucht?")
                Text("🦯🐓🔎🌽")
            }
        }
    }
}

class Emoji400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Sonic")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welcher Film wird gesucht?")
                Text("🦔🔵💨👟")
            }
        }
    }
}

class Emoji500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Ich glaube mein Schwein pfeift")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welches Sprichwort wird gesucht?")
                Text("🤔🐷😗🎵")
            }
        }
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
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Marke")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Was ergibt das Anagramm „ramke“?")
            }
        }
    }
}

class Wortkunst200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Im Regen stehen lassen")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welches Sprichwort ist hier verdreht?")
                Text("„Im Regen sitzen“")
            }
        }
    }
}

class Wortkunst300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Bank - Sitzgelegenheit, Geldinstitut, Sandbank")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welches deutsche Wort hat 3 Bedeutungen?")
            }
        }
    }
}

class Wortkunst400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Tat")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Welches Wort steckt in: „TOMATE“ + „RATTE“ – aber nicht in „MOTTE“?")
            }
        }
    }
}

class Wortkunst500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("Der Mensch - Kind krabbeln, Erwachsener normal, Alter mit Stock")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Das Rätsel der Sphinx")
                Text("Was geht am Morgen auf vier, am Mittag auf zwei und am Abend auf drei Beinen?")
            }
        }
    }
}

//==========================================================
// Fake or Real?
//==========================================================
class FakeOrReal100 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 100
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("A ist richtig (Metall dehnt sich aus)")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Was ist richtig?")
                Text("A) Der Eiffelturm ist im Sommer höher als im Winter.")
                Text("B) Der Mond ist größer als die Erde.")
            }
        }
    }
}

class FakeOrReal200 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 200
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("B ist richtig (z.B. durch Ring schwimmen)")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Was ist richtig?")
                Text("A) Goldfische haben ein 3-Sekunden-Gedächtnis.")
                Text("B) Goldfische können trainiert werden.")
            }
        }
    }
}

class FakeOrReal300 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 300
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("A ist richtig (einige tausend Jahre her)")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Was ist richtig?")
                Text("A) Die Sahara war einmal ein grüner Urwald.")
                Text("B) Es hat noch nie in Ägypten geschneit.")
            }
        }
    }
}

class FakeOrReal400 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 400
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("A ist richtig (es gibt Poststationen für Touristen)")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Was ist richtig?")
                Text("A) In der Antarktis gibt es eine Poststation.")
                Text("B) In Island gibt es keine Insekten.")
            }
        }
    }
}

class FakeOrReal500 : JeopardyQuestion {

    override fun getPoints(): Int {
        return 500
    }

    @Composable
    override fun render(window: GameWindow) {
        if (window == GameWindow.Moderation) {
            Column {
                Text("B ist richtig (ein untersuchtes Exemplar geschätzt Alter von 392±120 Jahren)")
            }
        }

        if (window == GameWindow.Presentation) {
            Column {
                Text("Was ist richtig?")
                Text("A) Menschen und Dinosaurier lebten zur gleichen Zeit.")
                Text("B) Einige Haie sind älter als Bäume.")
            }
        }
    }
}
