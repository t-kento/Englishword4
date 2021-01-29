package com.example.englishword4

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.home.*
import com.example.englishword4.BaseActivity as BaseActivity


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        click()
        getid()
    }

    private fun click() {
        registration.setOnClickListener {
            wordregistration()
        }

        wordlist.setOnClickListener {
            val wordlist = Intent(application, ListActivity::class.java)
            startActivity(wordlist)
        }

        test.setOnClickListener {
//            choiceTypeTest()
            val wordTest = Intent(application, WordTest::class.java)
            startActivity(wordTest)
        }
    }

    private fun getid() {
        val textidMain = EnglishWordApplication.loginId
        val className = findViewById<TextView>(R.id.classname)
        className.text = textidMain
    }

//    private fun choiceTypeTest(){
//        val type= arrayOf("日本語　→　英語","英語　→　日本語")
//        AlertDialog.Builder(this)
//            .setTitle("暗記テストの方法を選択")
//            .setItems(type) { dialog, which ->
//                val selected = arrayof[which]
//            }
//            .show()
//    }
}

