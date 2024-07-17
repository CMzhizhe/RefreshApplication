package com.gxx.refreshapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter

class PreLoadAdapter : BaseQuickAdapter<String, PreLoadAdapter.VH>() {

    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_text,parent,false)){
        val textView = itemView.findViewById<TextView>(R.id.tv_text)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: String?) {
        holder.textView.text = item
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
         return VH(parent)
    }
}