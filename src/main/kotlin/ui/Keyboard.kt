package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import keyBinds

@Composable
fun Keyboard(
    colors: Map<Char, Color>,
    enter: () -> Unit,
    backspace: () -> Unit,
    action: (Char) -> Unit
) {
    val requester = remember { FocusRequester() }

    val chars = listOf("QWERTYUIOP", "ASDFGHJKL")
    val lastRow = "ZXCVBNM"

    Column(
        modifier = Modifier.fillMaxSize().onKeyEvent(keyBinds(action, enter, backspace)).focusRequester(requester).focusable()
    ) {
        chars.forEach { rowChars ->
            Row(
                modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally)
            ) {
                rowChars.forEach {
                    KeyboardKey(it, colors[it] ?: Color.LightGray, action, requester)
                }
            }
        }

        Row(
            modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = { enter() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier.fillMaxHeight().aspectRatio(2f).padding(1.dp).widthIn(max=50.dp)
            ) {
                Text("Enter")
            }

            lastRow.forEach {
                KeyboardKey(it, colors[it] ?: Color.LightGray, action, requester)
            }

            Button(
                onClick = { backspace() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier.fillMaxHeight().aspectRatio(1.5f).padding(1.dp).widthIn(max=50.dp)
            ) {
                Image(
                    imageResource("icons/backspace.png"),
                    "backspace"
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        requester.requestFocus()
    }
}

@Composable
fun KeyboardKey(c: Char, color: Color, action: (Char) -> Unit, requester: FocusRequester) {
    Button(
        onClick = { requester.requestFocus(); action(c) },
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier.fillMaxHeight().aspectRatio(1.25f).padding(1.dp)
    ) {
        Text(c.toString())
    }
}