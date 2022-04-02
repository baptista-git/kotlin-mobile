package pt.baptista.android.training.hangmangame

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel

class HangmanViewModel(private val app: Application) : AndroidViewModel(app) {
    private val KEY_WORD = "KEY_WORD"
    private val KEY_RIGHT = "KEY_RIGHT"
    private val KEY_WRONG = "KEY_WRONG"

    private var gameState = HangmanGame.startGame()

    val visibleWord
        get() = gameState.word.map { c -> if (gameState.right.contains(c)) c else '_' }
            .joinToString("")

    val wrongLetters
        get() = gameState.wrong.joinToString("").padEnd(HangmanGame.maxWrong, '_')

    val numErrors
        get() = gameState.wrong.size

    fun guessWith(guess: String): String? {
        return when (guess.length) {
            0 -> app.getString(R.string.missing_letter)
            1 -> guessWith(guess[0])
            else -> app.getString(R.string.too_many_chars)
        }
    }

    private fun guessWith(guess: Char): String? {
        try {
            gameState = HangmanGame.guess(guess, gameState)
        } catch (error: HangmanException) {
            return when (error.type) {
                HangmanValidation.REPEATED -> app.getString(R.string.letter_already_used)
                HangmanValidation.NOT_A_LETTER -> app.getString(R.string.invalid_character)
            }
        }
        return null
    }

    fun saveState(outState: Bundle) {
        outState.putString(KEY_WORD, gameState.word)
        outState.putCharArray(KEY_RIGHT, gameState.right.toCharArray())
        outState.putCharArray(KEY_WRONG, gameState.wrong.toCharArray())
    }

    fun loadState(savedState: Bundle?) {
        val word = savedState?.getString(KEY_WORD)
        if (word != null)
            gameState = HangmanGameState(
                word,
                savedState.getCharArray(KEY_RIGHT)?.toSet() ?: setOf(),
                savedState.getCharArray(KEY_WRONG)?.toSet() ?: setOf()
            )
    }

    val gameResultMessage
        get() = when (HangmanGame.gameResult(gameState)) {
            HangmanResult.SUCCEEDED -> app.getString(R.string.congrats)
            HangmanResult.FAILED -> app.getString(R.string.hanged)
            HangmanResult.ONGOING -> null
        }
}