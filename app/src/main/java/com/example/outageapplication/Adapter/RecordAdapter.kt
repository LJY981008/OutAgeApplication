package com.example.outageapplication.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.outageapplication.Data.RecordItem
import com.example.outageapplication.R

class RecordAdapter(private val context: Context) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    var datas = mutableListOf<RecordItem>()
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: RecordAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false)
        return RecordAdapter.ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val v = view
        private val textCause: TextView = view.findViewById(R.id.tvCause)
        private val textDate: TextView = view.findViewById(R.id.tvDate)
        fun bind(item: RecordItem) {
            textCause.text = item.data["cause"]
            textDate.text = item.data["startDate"]
            v.setOnClickListener {

            }
        }
    }
}