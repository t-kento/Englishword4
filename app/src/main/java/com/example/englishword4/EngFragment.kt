package com.example.englishword4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragmen_english_list.*
import kotlinx.android.synthetic.main.fragment_jpanese_list.*
import kotlinx.android.synthetic.main.item_eng_list.*
import java.util.*

class EngFragment : Fragment() {

    private val engAdapter by lazy { EngAdapter(context!!) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragmen_english_list, container, false)
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
//        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

//    private fun initClick() {
//        fabEnglish.setOnClickListener {
//            (activity as? BaseActivity)?.also {
//                it.wordregistration()
//            }
//        }
//        reEnglish.setOnClickListener {
//            activity?.onBackPressed()
//        }
//    }

    private fun initRecyclerView() {
        engAdapter.callback = object : EngAdapter.EngAdapterCallback {
            override fun onClickDelete(itemView: AddWord) {
                deleteWord(itemView)
            }
        }
        EnglishRecyclerView.apply {
            adapter = engAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initSwipeRefreshLayout() {
        engSwipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun initData() {
        FirebaseFirestore.getInstance()
            .collection("word")
            .orderBy(AddWord::createdAt.name)
            .get()
            .addOnCompleteListener {
                engSwipeRefreshLayout.isRefreshing = false
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                var date = Date()
                it.result?.toObjects(AddWord::class.java)?.also { word ->
                    engAdapter.refresh(word)
                    date = word.lastOrNull()?.createdAt ?: Date()
                }
            }
    }

    private fun deleteWord(itemView:AddWord){
        FirebaseFirestore.getInstance()
            .collection("word")
            .document("${itemView.wordId}")
            .delete()
        engDeleteButton.visibility=View.INVISIBLE
    }

}