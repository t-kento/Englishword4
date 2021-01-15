package com.example.englishword4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragmen_english_list.*
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
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initClick() {
        fabEnglish.setOnClickListener {
            (activity as? BaseActivity)?.also { baseActivity ->
                baseActivity.wordregistration()
            }
        }
        reEnglish.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun initRecyclerView() {
        engAdapter.callback = object : EngAdapter.EngAdapterCallback {
            override fun onClickDelete(itemVIew: EngAdapter.ListObject) {
//                deleteWord()
            }
        }
        englishRecyclerView.apply {
            adapter = engAdapter
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
                    engAdapter.refresh(word)
                    date = word.lastOrNull()?.createdAt ?: Date()
                }
            }
    }

}