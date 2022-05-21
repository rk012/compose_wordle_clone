import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun BoxScope.EndScreen(win: Boolean, exit: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(1f, 1f, 0f, 0.5f)).align(Alignment.Center)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("You ${if (win) "win" else "lose"}!", textAlign = TextAlign.Center)
            Button(
                onClick = exit
            ) {
                Text("Exit")
            }
        }
    }
}