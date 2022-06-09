import io.github.rk012.wordle.Results
import io.github.rk012.wordle.getFilter

class WordleGame(private val target: String, val guesses: MutableList<String> = mutableListOf()) {
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
}