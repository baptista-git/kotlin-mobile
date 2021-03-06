package pt.baptista.android.training.hangmangame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class HangmanActivity : AppCompatActivity() {
    private val txtWord   by lazy { findViewById<TextView>(R.id.txtWord) }
    private val txtWrong  by lazy { findViewById<TextView>(R.id.txtWrong) }
    private val edtLetter by lazy { findViewById<EditText>(R.id.edtLetter) }
    private val butGuess  by lazy { findViewById<Button>(R.id.butGuess) }
    private val txtResult  by lazy { findViewById<TextView>(R.id.txtResult) }
    private val cvwGallows by lazy { findViewById<GallowsView>(R.id.cvwGallows) }

    private val viewModel by lazy { ViewModelProvider(this).get(HangmanViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman)

        if (savedInstanceState != null)
            viewModel.loadState(savedInstanceState)
        updateViews()
        butGuess.setOnClickListener { onGuess() }

        viewModel.history.observe(this, Observer { items ->
            Toast.makeText(this, "History items: ${items.size}", Toast.LENGTH_LONG).show()
            items.forEach {item -> Log.d("Hangman::history", "${item.id}: $item")  }
        })
    }

    private fun updateViews() {
        txtWord.text = viewModel.visibleWord.spaced()
        txtWrong.text = viewModel.wrongLetters.spaced()
        cvwGallows.steps = viewModel.numErrors

        viewModel.gameResultMessage?.let { gameResultMessage ->
            txtResult.text = gameResultMessage
            edtLetter.isEnabled = false
            butGuess.isEnabled = false
            viewModel.saveGame()
        }
    }

    private fun onGuess() {
        val guess = edtLetter.text.toString()
        when (val errorMsg = viewModel.guessWith(guess)) {
            null -> updateViews()
            else -> Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        }
        edtLetter.text.clear()
    }

    private fun String.spaced() = this.toCharArray().joinToString(" ")

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!isChangingConfigurations)
            viewModel.saveState(outState)
    }
}