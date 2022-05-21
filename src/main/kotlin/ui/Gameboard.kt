import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GameBoard(words: List<String>, colors: List<List<Color>>) {
    Column(
        modifier = Modifier.fillMaxHeight().aspectRatio(5/6f)
    ) {
        words.forEachIndexed { i, s ->
            if (i < colors.size)
                WordRow(s, colors[i])
            else
                WordRow(s)
        }
    }
}

@Composable
fun ColumnScope.WordRow(word: String, colors: List<Color> = List(5) { Color.LightGray }) {
    Row(
        modifier = Modifier.fillMaxHeight().weight(1f)
    ) {
        word.padEnd(5).forEachIndexed { i, c ->
            LetterTile(c, colors[i])
        }
    }
}

@Composable
fun RowScope.LetterTile(letter: Char, color: Color) {
    Box(
        modifier = Modifier.background(color).border(2.dp, Color.White).weight(1f).fillMaxHeight().aspectRatio(1f).align(Alignment.CenterVertically)
    ) {
        Text(letter.toString(), modifier = Modifier.align(Alignment.Center))
    }
}