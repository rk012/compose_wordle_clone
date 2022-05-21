import androidx.compose.ui.graphics.Color

class WordleGame(private val target: String, val guesses: MutableList<String> = mutableListOf()) {
    enum class Results(val color: Color) {
        MATCHES(Color.Green),
        EXISTS(Color.Yellow),
        NONE(Color.Gray)
    }

    val charColors: MutableMap<Char, Results> = mutableMapOf()

    val results: List<List<Results>>
        get() = guesses.map { getFilter(it, target) }

    fun addGuess(guess: String): List<Results> {
        guesses.add(guess)
        val filter = getFilter(guess, target)

        guess.forEachIndexed { i, c ->
            if (filter[i] == Results.MATCHES)
                charColors[c] = Results.MATCHES
            else if (filter[i] == Results.EXISTS && charColors[c] != Results.MATCHES)
                charColors[c] = Results.EXISTS
            else if (filter[i] == Results.NONE && charColors[c] == null)
                charColors[c] = Results.NONE
        }

        return filter
    }

    fun getFilter(input: String, target: String): List<Results> {
        val filter = MutableList(5) { Results.NONE }

        val letterCount = mutableMapOf<Char, Int>()
        target.forEach {
            letterCount[it] = letterCount.getDefault(it) + 1
        }

        target.forEachIndexed { index, c ->
            if (c == input[index]) {
                filter[index] = Results.MATCHES
                letterCount[c] = letterCount[c]!! - 1
            }
        }

        target.forEachIndexed { index, _ ->
            if (filter[index] != Results.MATCHES && letterCount.getDefault(input[index]) > 0) {
                filter[index] = Results.EXISTS
                letterCount[input[index]] = letterCount.getDefault(input[index]) - 1
            }
        }

        return filter
    }

    private fun<T> Map<T, Int>.getDefault(key: T): Int = get(key) ?: 0
}