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

class HomeActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen)
        intlogin()
    }

    private fun intlogin(){
        login.setOnClickListener{

            val inflater = layoutInflater
            val inflate_view = inflater.inflate(R.layout.login_view, null)
            val loginId = inflate_view.findViewById(R.id.loginid) as EditText

            AlertDialog.Builder(this)
                .setTitle("クラスへログイン")
                .setMessage("ログインIDまたはクラス名を入力")
                .setView(inflate_view)
                .setPositiveButton("登録") { _, _ ->
                    val documentid = loginId.text.toString()
                    logindocument(documentid)
                    idtransferBase(documentid)
                    idtransferMain(documentid)
                }
                .setNegativeButton("キャンセル") { _, _ -> }
                .show()
        }
        }

    private fun idtransferMain(documentId: String){
        val textMain = documentId
        val intentMain = Intent(application, MainActivity::class.java)
        //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
        intentMain.putExtra("TEXT_KEY_MAIN",textMain)
        //画面遷移を開始
        startActivity(intentMain)

    }
    private fun idtransferBase(documentId: String){
        val textBase = documentId
        println("${textBase+1}")
        println("${documentId+2}")
        println(textBase+3)
        println(documentId+4)

        val intentBaseActivity = Intent(application, BaseActivity::class.java)
        //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
        intentBaseActivity.putExtra("TEXT_KEY_BASE",textBase)

    }
    }
