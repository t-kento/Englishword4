package com.example.englishword4

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.InternalLogId
import kotlinx.android.synthetic.main.first_screen.*
import kotlinx.android.synthetic.main.login_view.*

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen)
        intlogin()
    }

    private fun intlogin() {
        login.setOnClickListener {

            val inflater = layoutInflater
            val inflate_view = inflater.inflate(R.layout.login_view, null)
            val loginId = inflate_view.findViewById(R.id.loginid) as EditText

            AlertDialog.Builder(this)
                .setTitle("クラスへログイン")
                .setMessage("ログインIDまたはクラス名を入力")
                .setView(inflate_view)
                .setPositiveButton("登録") { _, _ ->
                    val documentid = loginId.text.toString()
                    EnglishWordApplication.loginId = documentid
                    startActivity(Intent(this, MainActivity::class.java))

                }
                .setNegativeButton("キャンセル") { _, _ -> }
                .show()
        }
    }

//    private fun logindocument(documentid: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
////        val addWord = AddWord().apply {
////            password = "false"
////        }
//        EnglishWordApplication.loginId = documentid
//        FirebaseFirestore.getInstance()
//            .collection("word")
////            .add(addWord)
////            .addOnCompleteListener {
////                if (it.isSuccessful) {
////                    onSuccess.invoke()
////                } else {
////                    onFailure.invoke()
////                }
////            }
//    }
}
