package com.example.englishword4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_jpanese_list.*
import java.util.*

class JapFragment:Fragment() {

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
        fab.setOnClickListener{
            val main = MainActivity() 
            main.wordregistration()
        }
        re.setOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun initRecyclerView() {
        japAdapter.callback = object : JapAdapter.JapAdapterCallback {
            override fun onClickDelete(itemVIew: JapAdapter.ListObject) {
//                deleteWord()
            }
        }
        memoRecyclerView.apply {
            adapter = japAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }
    private fun initData() {
        FirebaseFirestore.getInstance()
            .collection("word")
            .orderBy(AddWord::createdAt.name)
            .get()
            .addOnCompleteListener {
                swipeRefreshLayout.isRefreshing = false
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
}