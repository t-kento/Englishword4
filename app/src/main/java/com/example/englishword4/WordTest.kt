package com.example.englishword4

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_jpanese_list.*
import kotlinx.android.synthetic.main.test_view.*
import java.util.*
import kotlin.random.Random

class WordTest : AppCompatActivity() {
    private var words = listOf<AddWord>()
    private var remainingword = mutableListOf<AddWord>()
    private var point: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_view)
        println("${words}+は何か")
        println("${remainingword}+はどうなるか")
        getwords()
        choice()
    }

    private fun getwords() {
        FirebaseFirestore.getInstance()
//            .collection(addWord.loginId)
            .collection("word")
            .get()
            .addOnCompleteListener {
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                var date = Date()
                it.result?.toObjects(AddWord::class.java)?.also { word ->
                    words = word
                    getRandomWord(word)
                    date = word.lastOrNull()?.createdAt ?: Date()
                }
            }
    }

    private fun intiTest() {
        if (words.isEmpty()) {
            Toast.makeText(this, "単語が登録されていません", Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }
        remainingword.clear()
        remainingword.addAll(words)
    }

    private fun getRandomWord(list: List<AddWord>): AddWord? {
        if (remainingword.isEmpty())
            return null
        var randomIndex: Int = Random.nextInt(0, remainingword.size - 1)

        val japanese_test = findViewById<TextView>(R.id.Japanese_test) as TextView
//        japanese_test.text = remainingword.Japaneseword


        return remainingword.removeAt(randomIndex)

        intiTest()
    }

    private fun choice() {
        answer.setOnClickListener {

        }
        correct.setOnClickListener {
            point++
            intiTest()
//            val japanese_test = findViewById<TextView>(R.id.Japanese_test) as TextView
//            japanese_test.text=Japaneseword.toString()
        }
        incorrect.setOnClickListener {
            intiTest()
        }

    }
}
