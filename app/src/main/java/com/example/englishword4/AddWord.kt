package com.example.englishword4

import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class AddWord {
    var password: String = "true"
    var Japaneseword: String = ""
    var Englishword: String = ""
    var wordId: String = "${System.currentTimeMillis()}"
    var createdAt: Date = Date()
    var className : String=EnglishWordApplication.loginId
}

