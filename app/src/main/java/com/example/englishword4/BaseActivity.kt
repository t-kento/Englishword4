package com.example.englishword4

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

open class BaseActivity:AppCompatActivity() {

    fun wordregistration() {
        val inflater = layoutInflater
        val inflate_view = inflater.inflate(R.layout.custom_view, null)

        val japanese_text = inflate_view.findViewById(R.id.japanese_text) as EditText
        val english_text = inflate_view.findViewById(R.id.english_text) as EditText

        AlertDialog.Builder(this)
            .setTitle("単語登録")
            .setMessage("単語を入力してください")
            .setView(inflate_view)
            .setPositiveButton("登録") { _, _ ->
                val japanese = japanese_text.text.toString()
                val english = english_text.text.toString()
                addtext(japanese, english)
                Toast.makeText(this, "日本語：${japanese}\n英　語：${english}\n登録完了", Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton("キャンセル") { _, _ -> }
            .show()
    }

    private fun addtext(japanese: String, english: String) {
        val intentBaseActivity =  intent.getStringExtra("TEXT_KEY_BASE").toString()
        println(intentBaseActivity)
        val addWord = AddWord().apply {
            loginId=intentBaseActivity
            Japaneseword = japanese
            Englishword = english
        }
        FirebaseFirestore.getInstance()
            .collection("${intentBaseActivity}")
            .document(addWord.wordId)
            .set(addWord)
    }
    internal fun logindocument(documentid:String){
        val addWord = AddWord().apply {
            loginId = "${documentid}が作られました"
        }
        FirebaseFirestore.getInstance()
            .collection(documentid)
            .add(addWord)
    }
}