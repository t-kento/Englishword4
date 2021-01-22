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


class MainActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        click()
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

        }
    }



}
