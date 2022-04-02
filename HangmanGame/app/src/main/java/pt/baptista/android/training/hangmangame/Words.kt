package pt.baptista.android.training.hangmangame

import android.util.Log
import android.widget.Toast
import java.util.*
import kotlin.random.Random

object Words {
    const val PT = "PT"
    private val wordsPT = arrayOf(
        "peculiar", "respeito", "prudente", "alienado",
        "abstrato", "conceito", "devaneio", "conserto",
        "respaldo", "concerne", "fomentar", "proceder",
        "apologia", "conserto", "reiterar", "ardiloso"
    )
    private val wordsDEF = arrayOf(
        "aircraft", "athletic", "baseball", "building",
        "ceremony", "consumer", "disorder", "economic",
        "firewall", "guardian", "heritage", "judgment",
        "location", "memorial", "notebook", "overhead",
        "platform", "question", "reliance", "shoulder",
        "thousand", "umbrella", "volatile", "wildlife"
    )

    private val rand = Random(System.nanoTime())

    fun getRandomWord(): String{
        return if(Locale.getDefault().country == PT) wordsPT[rand.nextInt(wordsPT.size)]
        else wordsDEF[rand.nextInt(wordsDEF.size)]
    }

}