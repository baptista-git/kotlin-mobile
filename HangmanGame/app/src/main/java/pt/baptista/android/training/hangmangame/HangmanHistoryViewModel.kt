package pt.baptista.android.training.hangmangame

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class HangmanHistoryViewModel (private val app:Application):AndroidViewModel(app){
    private val repository = app.repository

    val history
        get() = repository.allHistoryItems
}