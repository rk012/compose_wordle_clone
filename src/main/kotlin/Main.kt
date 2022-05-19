import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

fun main() = Window {
    val colors = mapOf<Char, Color>()
    var text by remember { mutableStateOf("") }

    Column {
        Text(text, modifier = Modifier.weight(6f), color = Color.Black)
        Row(modifier = Modifier.weight(1f)) {}
        Row(
            modifier = Modifier.weight(3f)
        ) {
            Keyboard(colors, enter = { text = "" }) { text += it.toString() }
        }
    }
}