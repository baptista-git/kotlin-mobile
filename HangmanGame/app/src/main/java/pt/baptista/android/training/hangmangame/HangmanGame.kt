package pt.baptista.android.training.hangmangame

import java.lang.Exception
import java.util.*

object HangmanGame {
    const val maxWrong = 7

    fun gameResult(state: HangmanGameState) =
        when{
            state.wrong.size == maxWrong -> HangmanResult.FAILED
            state.word.all{state.right.contains(it)} -> HangmanResult.SUCCEEDED
            else-> HangmanResult.ONGOING
        }

    fun startGame() = HangmanGameState(Words.getRandomWord().uppercase(Locale.getDefault()))

    fun guess(guess:Char, state: HangmanGameState)=
        if(state.word.contains(validate(guess.uppercaseChar(), state)))
            HangmanGameState(state.word, state.right + guess, state.wrong)
        else
            HangmanGameState(state.word, state.right, state.wrong + guess)

    private fun validate(guess: Char, state: HangmanGameState)=
        when(guess){
            !in 'A'..'Z' -> throw HangmanException(HangmanValidation.NOT_A_LETTER)
            in state.right, in state.wrong -> throw HangmanException(HangmanValidation.REPEATED)
            else -> guess
        }
}

class HangmanException(val type: HangmanValidation) : Exception()

enum class HangmanValidation{
    REPEATED, NOT_A_LETTER
}
enum class  HangmanResult{
    FAILED, SUCCEEDED, ONGOING
}
