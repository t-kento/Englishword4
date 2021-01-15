package com.example.englishword4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_eng_list.view.*
import kotlinx.android.synthetic.main.item_jap_list.view.*
import kotlinx.android.synthetic.main.item_jap_list.view.deleteButton
import kotlinx.android.synthetic.main.item_jap_list.view.number_text

class EngAdapter (private val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<AddWord>()

    var callback: EngAdapterCallback? = null

    fun refresh(list: List<AddWord>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_eng_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemView = items[position]
        holder.apply {
            number_text.text="${position + 1}"
            edit_text.text = itemView.Englishword
            deleteButton.setOnClickListener {
//                callback?.onClickDelete()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number_text = view.number_text
        val edit_text = view.English_text
        val deleteButton = view.deleteButton
    }

    interface EngAdapterCallback {
        fun onClickDelete(itemVIew: ListObject)
    }

    open class ListObject {
        val number_text = ""
        val edit_text = ""
    }
}