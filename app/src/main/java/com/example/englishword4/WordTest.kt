package com.example.englishword4

import android.app.AlertDialog
import android.content.Intent
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
    private var answerIsEnglish = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_view)
        showDialog()
        getWords()
        choice()
    }

    private fun getWords() {
        FirebaseFirestore.getInstance()
            .collection("word")
            .whereEqualTo(AddWord::deletedAt.name, null)
            .whereEqualTo(AddWord::className.name, EnglishWordApplication.loginId)
            .get()
            .addOnCompleteListener {
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                it.result?.toObjects(AddWord::class.java)?.also { word ->
                    words = word
                    initTest()
                }
            }
    }

    private fun showDialog() {
        val strlist = arrayOf("日本語　→　英語", "英語　→　日本語")

        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("どちらのタイプでテストしますか？")
            .setItems(strlist) { dialog, which ->
                answerIsEnglish = which == 0
                initTest()
            }
            .show()
    }

    private fun initTest() {
        if (words.isEmpty()) {
            Toast.makeText(this, "単語が登録されていません", Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }
        point = 0
        remainingword.clear()
        remainingword.addAll(words)

        showQuestion()
    }

    private fun getRandomWord(): AddWord? {
        if (remainingword.isEmpty())
            return null
        var randomIndex: Int = Random.nextInt(remainingword.size)

        return remainingword.removeAt(randomIndex)
    }

    private fun choice() {
        answer.setOnClickListener {
            showAnswer()
        }
        correct.setOnClickListener {
            point++
            showQuestion()
        }
        incorrect.setOnClickListener {
            showQuestion()
        }
    }

    private fun showQuestion() {
        val addWord = getRandomWord()
        if (addWord == null) {
            AlertDialog.Builder(this)
                .setTitle("結果発表！")
                .setMessage("得点は　${point} / ${words.size}")
                .setPositiveButton("もう一度") { _, _ ->
                    showDialog()
                }
                .setNegativeButton("やめる") { _, _ ->
                    Toast.makeText(
                        this,
                        "テストは終了しました",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .show()
            return
        }

        buttonLayout.visibility = View.INVISIBLE
        currentAddWord = addWord
        English_answer.text = if (answerIsEnglish) "" else addWord.Englishword
        Japanese_test.text = if (answerIsEnglish) addWord.Japaneseword else ""
    }

    private fun showAnswer() {
        Japanese_test.text = currentAddWord?.Japaneseword
        English_answer.text = currentAddWord?.Englishword
        buttonLayout.visibility = View.VISIBLE
    }
}
