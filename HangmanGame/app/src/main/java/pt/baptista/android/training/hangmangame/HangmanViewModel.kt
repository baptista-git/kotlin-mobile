package pt.baptista.android.training.hangmangame

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import java.util.Date

class HangmanViewModel(private val app: Application) : AndroidViewModel(app) {

    private val repository = app.repository

    private var gameState = HangmanGame.startGame()

    private var saved = false;

    val visibleWord
        get() = gameState.word.map { c -> if (gameState.right.contains(c)) c else '_' }
            .joinToString("")

    val wrongLetters
        get() = gameState.wrong.joinToString("").padEnd(HangmanGame.maxWrong, '_')

    val numErrors
        get() = gameState.wrong.size

    val gameResultMessage
        get() = when (HangmanGame.gameResult(gameState)) {
            HangmanResult.SUCCEEDED -> app.getString(R.string.congrats)
            HangmanResult.FAILED -> app.getString(R.string.hanged)
            HangmanResult.ONGOING -> null
        }

    val history
        get() = repository.allHistoryItems

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

    fun saveGame() {
        if (!saved && HangmanGame.gameResult(gameState) != HangmanResult.ONGOING) {
            repository.insertHistoryItem(
                HangmanHistoryItem(
                    gameState.word,
                    gameState.right.joinToString(""),
                    gameState.wrong.joinToString(""),
                    Date()
                )
            )
        }
    }

    private val KEY_WORD = "KEY_WORD"
    private val KEY_RIGHT = "KEY_RIGHT"
    private val KEY_WRONG = "KEY_WRONG"
    private val KEY_SAVED = "KEY_SAVED"

    fun saveState(outState: Bundle) {
        outState.putString(KEY_WORD, gameState.word)
        outState.putCharArray(KEY_RIGHT, gameState.right.toCharArray())
        outState.putCharArray(KEY_WRONG, gameState.wrong.toCharArray())
        outState.putBoolean(KEY_SAVED, saved)
    }

    fun loadState(savedState: Bundle?) {
        savedState?.getString(KEY_WORD)?.let { word->
            gameState = HangmanGameState(
                word,
                savedState.getCharArray(KEY_RIGHT)?.toSet() ?: setOf(),
                savedState.getCharArray(KEY_WRONG)?.toSet() ?: setOf()
            )
            saved = savedState.getBoolean(KEY_SAVED)
        }
    }
}