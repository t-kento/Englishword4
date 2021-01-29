package com.example.englishword4

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.custom_view.*
import kotlinx.android.synthetic.main.fragment_jpanese_list.*
import kotlinx.android.synthetic.main.item_eng_list.*
import kotlinx.android.synthetic.main.test_view.*
import java.util.*
import kotlin.random.Random

class WordTest : AppCompatActivity() {
    private var words = listOf<AddWord>()
    private var remainingword = mutableListOf<AddWord>()
    private var point: Int = 0
    private var currentAddWord: AddWord? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_view)
        getWords()
        choice()
    }

    private fun getWords() {
        FirebaseFirestore.getInstance()
            .collection("word")
            .get()
            .addOnCompleteListener {
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                it.result?.toObjects(AddWord::class.java)?.also { word ->
                    words = word
                    words.forEach{
                        println("${it.Japaneseword}")
                    }
                    getRandomWord()
                    intiTest()
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
        English_answer.visibility=View.INVISIBLE
        remainingword.clear()
        remainingword.addAll(words)

        showQuestion()
    }

    private fun getRandomWord(): AddWord? {
        if (remainingword.isEmpty())
            return null
        var randomIndex: Int = Random.nextInt(remainingword.size)

//        var japanese_test = findViewById<TextView>(R.id.Japanese_test) as TextView
////        japanese_test.text = remainingword.add(Japaneseword)

        intiTest()
        return remainingword.removeAt(randomIndex)
    }

    private fun choice() {
        answer.setOnClickListener {
            showAnswer()
        }
        correct.setOnClickListener {
            point++
            English_answer.text
            buttonLayout.visibility=View.INVISIBLE
            intiTest()
        }
        incorrect.setOnClickListener {
            intiTest()
            buttonLayout.visibility=View.INVISIBLE
        }
    }

    private fun showQuestion() {
        val addWord = getRandomWord()
        if (addWord == null) {
            Toast.makeText(this, "テストは終了しました", Toast.LENGTH_SHORT).show()
            return
        }
        currentAddWord = addWord
        Japanese_test.text = addWord.Japaneseword
    }

    private fun showAnswer() {
        English_answer.text = currentAddWord?.Englishword
        buttonLayout.visibility= View.VISIBLE
    }
}
