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
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private val perPageCount = 1000      // 데이터 총 개수
    private val pageCount = 1           // 표시할 페이지 수
    private val itemList = mutableListOf<RecordItem>()
    private var local = "전체"
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

    override fun onResume() {
        super.onResume()
        setFab()
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

    /**
     * 단수 내역 리스트 세팅
     */
    private fun setList(contents: RecordBody) {
        itemList.clear()
        adapter.datas.clear()
        contents.data.forEach {
            itemList.add(RecordItem(it.getRecordMap()))
        }
        itemList.sortBy { it.getStartDate() }       // 단수 시작일 기준으로 정렬
        itemList.reverse()          // 최상위에 가장 최신의 정보

        if (local == "전체")                          // 지역 선택의 경우 필터링
            adapter.datas = itemList
        else {
            val tmpList = mutableListOf<RecordItem>()
            itemList.forEach {
                if (it.getLocal() == local) {
                    tmpList.add(it)
                }
            }
            adapter.datas = tmpList
        }

        adapter.notifyDataSetChanged()
    }

    /**
     * fab버튼 리스너 등록
     */
    private fun setFab() {
        MainActivity.fabBtn.setOnClickListener {
            val localList = ArrayList<String>()
            itemList.forEach {
                localList.add(it.getLocal())
            }
            val tmpList: ArrayList<String> = localList.distinct() as ArrayList<String>
            val bundle = Bundle()
            bundle.putStringArrayList("localList", tmpList)
            selectRecordDialog.arguments = bundle
            selectRecordDialog.show(this.childFragmentManager, "select")
            selectRecordDialog.setOnClickListener(object :
                SelectRecordDialog.OnDialogClickListener {
                override fun onClicked(tmp: String) {
                    local = tmp
                    getRecordData()
                }
            })
        }
    }

}