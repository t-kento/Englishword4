package com.example.englishword4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_jpanese_list.*
import java.util.*
import kotlin.random.Random

class WordTest : AppCompatActivity() {
    private var words = mutableListOf<AddWord>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_view)
        getwords()
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
                    getRandomWord(word)
                    date = word.lastOrNull()?.createdAt ?: Date()
                }
            }
    }

    private fun intiTest(){
    }

    private fun getRandomWord(list: List<AddWord>): AddWord? {
        if (words.isEmpty())
            return null
        var randomIndex : Int = Random.nextInt(0,words.size-1)

        return words[randomIndex]
    }

}
