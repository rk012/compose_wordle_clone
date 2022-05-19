import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BoxScope.EndScreen(win: Boolean, exit: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(.4f).aspectRatio(1f).background(Color.Yellow).align(Alignment.Center)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("You ${if (win) "win" else "lose"}!")
            Button(
                onClick = exit
            ) {
                Text("Exit")
            }
        }
    }
}