package pt.baptista.android.training.hangmangame

import android.app.Application
import androidx.room.Room

class HangmanApp : Application() {
    //private var database : HangmanHistoryDatabase? = null //worst option - need to avoid nullable
    //private lateinit var database : HangmanHistoryDatabase //middle option - need to initialize on create
    private val database by lazy { //best option - concise
        Room.databaseBuilder(this, HangmanHistoryDatabase::class.java, "history_db")
            .fallbackToDestructiveMigration()
            .build()
    }

//    override fun onCreate() {
//        super.onCreate()
//        Room.databaseBuilder(this, HangmanHistoryDatabase::class.java, "hangman_history_db")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
    val repository by lazy {
        HangmanRepository(database)
    }
}
val Application.repository
    get() = (this as HangmanApp).repository