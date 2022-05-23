import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.EndScreen
import ui.GameBoard
import ui.Keyboard
import java.net.URL
import javax.imageio.ImageIO
import kotlin.system.exitProcess

val solution_list = getResource("wordlists/solution_list.txt")!!.readText().split("\r\n")
val valid_list = getResource("wordlists/valid_list.txt")!!.readText().split("\r\n") + solution_list

val target = solution_list.random().uppercase()

val game = WordleGame(target)

fun getResource(path: String): URL? = object {}::class.java.getResource(path)

fun main() = Window(
    title = "Wordle",
    icon = ImageIO.read(getResource("icons/wordle.png")),
) {
    var guesses by remember { mutableStateOf(listOf<String>()) }

    var warningText by remember { mutableStateOf("") }

    var hardMode by remember { mutableStateOf(false) }
    var hardModeSwitchEnabled by remember { mutableStateOf(true) }

    var currentWord by remember { mutableStateOf("") }
    var finished by remember { mutableStateOf(false) }
    var win by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.align(Alignment.TopCenter).fillMaxHeight(.7f)
        ) {
            GameBoard(
                guesses.padEnd(6, currentWord, ""),
                game.results.map { l -> l.map { it.color } },
                warningText,
                hardModeSwitchEnabled = hardModeSwitchEnabled,
                hardModeEnabled = hardMode,
                onHardModeChange = {
                    hardMode = it
                },
            )
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxHeight(.2f)
        ) {
            Keyboard(
                game.charColors.mapValues { it.value.color },
                enter = {
                    if (currentWord.length != 5) {
                        warningText = "Not enough letters"
                    } else if (currentWord.lowercase() !in valid_list) {
                        warningText = "Not a valid word"
                    } else if (hardMode && game.results != guesses.map { game.getFilter(it, currentWord) }) {
                        warningText = "Guess must use previous information"
                    } else {
                        warningText = ""

                        if (!hardMode) {
                            hardModeSwitchEnabled = false
                        }

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