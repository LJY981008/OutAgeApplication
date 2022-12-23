package com.example.outageapplication.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.outageapplication.Data.Drought
import com.example.outageapplication.Data.DroughtBody
import com.example.outageapplication.Data.DroughtItem
import com.example.outageapplication.R

class SearchAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {

    var datas = mutableListOf<DroughtItem>()
    var saveDatas = mutableListOf<DroughtItem>()
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(context).inflate(R.layout.item_dought, parent, false)
        return ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val v = view
        private val textLocal: TextView = view.findViewById(R.id.tvLocal)
        private val textFacility: TextView = view.findViewById(R.id.tvFacility)
        private val textStep: TextView = view.findViewById(R.id.tvStep)
        private val textYield: TextView = view.findViewById(R.id.tvYield)
        private val textPrepare: TextView = view.findViewById(R.id.tvPrepare)
        private val textAve: TextView = view.findViewById(R.id.tvAve)
        fun bind(item: DroughtItem) {
            textLocal.text = item.data["local"]
            textFacility.text = item.data["facility"]
            textStep.text =item.data["step"]
            textYield.text = item.data["yield"]
            textPrepare.text = item.data["prepare"]
            textAve.text = item.data["ave"]
            v.setOnClickListener {
            }
        }
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                datas = if (charString.isEmpty()) {
                    saveDatas
                } else {
                    val filteredList = mutableListOf<DroughtItem>()
                    var isCheck = false
                    saveDatas.forEach {
                        if (it!!.data["local"]!!.contains(charString)) {
                            filteredList.forEach { i->
                                if(it.data["local"] == i.data["local"])
                                    isCheck = true
                            }
                            if(!isCheck) {
                                filteredList.add(it)
                            }
                        }
                    }
                    isCheck = false
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = datas
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                datas = results.values as ArrayList<DroughtItem>
                notifyDataSetChanged()
            }
        }
    }


}