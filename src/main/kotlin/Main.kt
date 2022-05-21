import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlin.system.exitProcess

val solution_list = getResource("wordlists/solution_list.txt")!!.split("\r\n")
val valid_list = getResource("wordlists/valid_list.txt")!!.split("\r\n") + solution_list

val target = solution_list.random().uppercase()

val game = WordleGame(target)

fun getResource(path: String) = object {}::class.java.getResource(path)?.readText()

fun main() = Window {
    var guesses by remember { mutableStateOf(listOf<String>()) }

    var currentWord by remember { mutableStateOf("") }
    var finished by remember { mutableStateOf(false) }
    var win by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.align(Alignment.TopCenter).fillMaxHeight(.7f)
        ) {
            GameBoard(guesses.padEnd(6, currentWord, ""), game.results.map { l -> l.map { it.color } })
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxHeight(.2f)
        ) {
            Keyboard(
                game.charColors.mapValues { it.value.color },
                enter = {
                    if (currentWord.length == 5 && currentWord.lowercase() in valid_list) {
                        val results = game.addGuess(currentWord)

                        guesses = game.guesses
                        currentWord = ""

                        if (game.guesses.size == 6) {
                            win = false
                            finished = true
                        }

                        if (results.all { it == WordleGame.Results.MATCHES }) {
                            win = true
                            finished = true
                        }
                    }
                },
                backspace = {
                    currentWord = currentWord.dropLast(1)
                }
            ) {
                currentWord = (currentWord + it).take(5)
            }
        }

        if (finished) {
            EndScreen(win) { exitProcess(0) }
        }
    }
}

fun List<String>.padEnd(n: Int, currentWord: String, value: String): List<String> = when (size) {
    n - 1 -> this + listOf(currentWord)
    n -> this
    else -> this + listOf(currentWord) + List(n - 1 - size) { value }
}