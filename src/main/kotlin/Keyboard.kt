import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Keyboard(colors: Map<Char, Color>, enter: () -> Unit, action: (Char) -> Unit) {
    val chars = listOf("qwertyuiop", "asdfghjkl")
    val lastRow = "zxcvbnm"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        chars.forEach { rowChars ->
            Row(
                modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally)
            ) {
                rowChars.forEach {
                    KeyboardKey(it, colors[it] ?: Color.Gray, action)
                }
            }
        }

        Row(
            modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally)
        ) {
            lastRow.forEach {
                KeyboardKey(it, colors[it] ?: Color.Gray, action)
            }

            Button(
                onClick = { enter() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                modifier = Modifier.fillMaxHeight().aspectRatio(2f).padding(1.dp).widthIn(max=50.dp)
            ) {
                Text("Enter")
            }
        }
    }
}

@Composable
fun KeyboardKey(c: Char, color: Color, action: (Char) -> Unit) {
    Button(
        onClick = { action(c) },
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier.fillMaxHeight().aspectRatio(1.25f).padding(1.dp)
    ) {
        Text(c.toString())
    }
}