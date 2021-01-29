package com.example.englishword4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishword4.EnglishWordApplication.Companion.loginId
import com.google.firebase.firestore.FieldValue.delete
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.util.FileUtil.delete
import kotlinx.android.synthetic.main.fragment_jpanese_list.*
import kotlinx.android.synthetic.main.item_jap_list.*
import kotlinx.android.synthetic.main.login_view.*
import java.nio.file.Files.delete
import java.util.*

class JapFragment : Fragment() {

    private val japAdapter by lazy { JapAdapter(context!!) }
    private var words = listOf<AddWord>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_jpanese_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initLayout()
        initData()
    }

    private fun initLayout() {
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initRecyclerView() {
        japAdapter.callback = object : JapAdapter.JapAdapterCallback {
            override fun onClickDelete(itemView: AddWord) {
                deleteWord(itemView)
            }
        }
        memoRecyclerView.apply {
            adapter = japAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initSwipeRefreshLayout() {
        japSwipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun initData() {
        FirebaseFirestore.getInstance()
            .collection("word")
            .orderBy(AddWord::createdAt.name)
//            .whereEqualTo(AddWord::deletedAt,null)
            .get()
            .addOnCompleteListener {
                japSwipeRefreshLayout.isRefreshing = false
                if (!it.isSuccessful)
                    println("失敗しました")
                return@addOnCompleteListener

                it.result?.toObjects(AddWord::class.java)?.also { word ->
//                    words=word
                    japAdapter.refresh(word)
//                    date = word.lastOrNull()?.createdAt ?: Date()
                }
            }
    }

    private fun deleteWord(itemView: AddWord) {
        FirebaseFirestore.getInstance()
            .collection("word")
            .document(itemView.wordId)
            .set(itemView.apply {
                deletedAt = Date()
            })
        japDeleteButton.visibility = View.INVISIBLE
    }
}