package com.example.englishword4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FieldValue.delete
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.util.FileUtil.delete
import kotlinx.android.synthetic.main.fragment_jpanese_list.*
import java.nio.file.Files.delete
import java.util.*

class JapFragment :Fragment() {

    private val japAdapter by lazy { JapAdapter(context!!) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_jpanese_list, container, false)
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
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initClick(){
        fabJapanese.setOnClickListener{
            (activity as? BaseActivity)?.also{
                it.wordregistration()
            }
        }
        reJapanese.setOnClickListener{
            activity?.onBackPressed()
        }
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
        val addWord = AddWord().apply {

        }
        FirebaseFirestore.getInstance()
//            .collection(addWord.loginId)
            .collection("word")
            .orderBy(AddWord::createdAt.name)
            .get()
            .addOnCompleteListener {
                japSwipeRefreshLayout.isRefreshing = false
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                var date = Date()
                it.result?.toObjects(AddWord::class.java)?.also { word ->
//                    val recent = word.filter{it.createdAt.time > (System.currentTimeMillis()-7*24*60*60*1000L)}
//                    println("$recent")
                    japAdapter.refresh(word)
                    date = word.lastOrNull()?.createdAt ?: Date()
                }
            }
    }

    private fun deleteWord(itemView:AddWord){
        val addWord = AddWord()
        FirebaseFirestore.getInstance()
//            .collection(addWord.loginId)
            .collection("word")
            .document("${itemView.wordId}")
            .delete()
    }
}