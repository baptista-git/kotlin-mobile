package pt.baptista.android.training.hangmangame

data class HangmanGameState(
    val word:String,
    val right:Set<Char> = emptySet(),
    val wrong:Set<Char> = emptySet()
)