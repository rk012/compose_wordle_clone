import androidx.compose.ui.input.key.*

fun keyBinds(
    sendChar: (Char) -> Unit,
    enter: () -> Unit,
    backspace: () -> Unit
): (KeyEvent) -> Boolean = keyEvent@{
    if (it.type != KeyEventType.KeyDown) return@keyEvent false

    when (it.key) {
        Key.Enter -> enter()
        Key.Backspace -> backspace()
        else -> {
            if (it.key.toString().lowercase().last() in 'a'..'z') {
                sendChar(it.key.toString().uppercase().last())
            } else {
                println(it.key)
                return@keyEvent false
            }
        }
    }

    true
}