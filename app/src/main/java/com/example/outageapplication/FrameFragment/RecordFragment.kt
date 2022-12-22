package com.example.outageapplication.FrameFragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.logging.SimpleFormatter

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    private val selectRecordDialog = SelectRecordDialog()
    private lateinit var adapter: RecordAdapter
    private val perPageCount = 240      // 데이터 총 개수
    private val pageCount = 1           // 표시할 페이지 수
    private val itemList = mutableListOf<RecordItem>()
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
        RetrofitRecordObject.getApiFacilityService().getInfo(perPageCount, pageCount)
            .enqueue(object : Callback<RecordBody> {
                override fun onResponse(call: Call<RecordBody>, response: Response<RecordBody>) {
                    Log.d(
                        "개수",
                        "${response.body()!!.totalCount}, ${response.body()!!.currentCount}, ${response.body()!!.matchCount}"
                    )
                    setList(response.body()!!)
                }

                override fun onFailure(call: Call<RecordBody>, t: Throwable) {
                }
            })
    }

    private fun setList(contents: RecordBody) {
        contents.data.forEach {
            itemList.add(RecordItem(it.getRecordMap()))
        }
        itemList.sortBy { it.getStartDate() }       // 단수 시작일 기준으로 정렬
        itemList.reverse()          // 최상위에 가장 최신의 정보
        adapter.datas = itemList
        adapter.notifyDataSetChanged()
    }


    override fun onResume() {
        super.onResume()
        MainActivity.fabBtn.setOnClickListener {
            selectRecordDialog.show(this.childFragmentManager, "select")
        }
    }
}