package com.example.englishword4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_jap_list.view.*

class JapAdapter(private val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<AddWord>()

    var callback: JapAdapterCallback? = null

    fun refresh(list: List<AddWord>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.fragment_jpanese_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemView = items[position]
        holder.apply {
//            number_text.text=""
//            edit_text.text = itemView.Japaneseword
            deleteButton.setOnClickListener {
//                callback?.onClickDelete()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number_text = view.number_text
        val edit_text = view.japanese_text
        val deleteButton = view.deleteButton
    }

    interface JapAdapterCallback {
        fun onClickDelete(itemVIew: ListObject)
    }

    open class ListObject {
        val number_text = ""
        val edit_text = ""
    }
}