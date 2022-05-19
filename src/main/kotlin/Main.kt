import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.system.exitProcess

fun main() = Window {
    val colors = mapOf<Char, Color>()
    var text by remember { mutableStateOf("") }
    var finished by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.align(Alignment.TopCenter).fillMaxHeight(.7f)
        ) {
            GameBoard(listOf("raise", "juicy", text, "", "", ""), text)
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxHeight(.2f)
        ) {
            Keyboard(colors, enter = { text = ""; finished = !finished }) { text += it.toString() }
        }

        if (finished) {
            EndScreen(true) { exitProcess(0) }
        }
    }
}