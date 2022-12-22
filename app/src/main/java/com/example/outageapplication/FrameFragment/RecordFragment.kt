package com.example.outageapplication.FrameFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.outageapplication.Adapter.RecordAdapter
import com.example.outageapplication.Data.RecordBody
import com.example.outageapplication.Data.RecordItem
import com.example.outageapplication.Dialog.SelectRecordDialog
import com.example.outageapplication.Interface.RetrofitRecordObject
import com.example.outageapplication.MainActivity
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentRecordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    private val selectRecordDialog = SelectRecordDialog()
    private lateinit var adapter: RecordAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        adapter = RecordAdapter(this.requireContext())
        binding.rvProfile.adapter = adapter
        getRecordData()
        return binding.root
    }

    /**
     * 단수내역 호출
     */
    private fun getRecordData() {
        RetrofitRecordObject.getApiFacilityService().getInfo(10, 1)
            .enqueue(object : Callback<RecordBody>{
            override fun onResponse(call: Call<RecordBody>, response: Response<RecordBody>) {
                setList(response.body()!!)
            }

            override fun onFailure(call: Call<RecordBody>, t: Throwable) {
            }
        })
    }
    private fun setList(contents: RecordBody){
        val list = mutableListOf<RecordItem>()
        contents.data.forEach {
            list.add(RecordItem(it.getRecordMap()))
        }
        adapter.datas = list
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        MainActivity.fabBtn.setOnClickListener {
            selectRecordDialog.show(this.childFragmentManager, "select")
        }
    }
}