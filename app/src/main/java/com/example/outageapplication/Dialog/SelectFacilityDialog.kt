package com.example.outageapplication.Dialog

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.outageapplication.Data.Facility
import com.example.outageapplication.Data.FacilityBody
import com.example.outageapplication.Interface.RetrofitFacilityObject
import com.example.outageapplication.MainActivity
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentSelectFacilityDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectFacilityDialog : DialogFragment() {
    private lateinit var binding: FragmentSelectFacilityDialogBinding
    private lateinit var spinner: Spinner
    private lateinit var spinnerDetail: Spinner
    private lateinit var onClickListener: OnDialogClickListener
    private lateinit var facility:FacilityBody
    private val loadingDialog = LoadingDialog()
    private var listLocation = mutableListOf<String>()
    private var items = arrayOf(
        "송파구",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectFacilityDialogBinding.inflate(inflater, container, false)
        spinner = binding.spinner
        spinnerDetail = binding.spinnerDetail

        spinner.adapter = setSpinnerLocationAdapter()
        spinner.setSelection(0)
        spinner.dropDownVerticalOffset = dipToPixels(45f).toInt()
        getMapFacilityData(spinner.selectedItem as String)
        setSelectedListener()
        
        //여기
        // 현재 fab을 눌러서 확인을 눌러도 마커가 안찍힘
        // 주소도 저장 방식 변경


        return binding.root
    }

    /**
     * 급수시설 호출
     */
    private fun getMapFacilityData(selectLocation: String) {
        RetrofitFacilityObject.getApiFacilityService().getInfo(94, 1)
            .enqueue(object : Callback<FacilityBody> {
                override fun onResponse(
                    call: Call<FacilityBody>,
                    response: Response<FacilityBody>
                ) {

                    if (response.code() == 200) {
                        facility = response.body()!!
                        detailLocation(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<FacilityBody>, t: Throwable) {
                    Log.d("실패", t.message.toString())
                }

            })
    }

    private fun detailLocation(contents: FacilityBody) {
        contents.data.forEach {
            listLocation.add(it.getDetailLocation())
        }
        var listDetail: List<String> = listLocation.distinct()
        setSpinnerDetailLocationAdapter(listDetail)
    }


    private fun setSelectedListener() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getMapFacilityData(spinner.selectedItem as String)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                spinner.setSelection(0)
            }
        }
    }

    private fun setSpinnerDetailLocationAdapter(list: List<String>) {
        val spinnerAdapter =
            object : ArrayAdapter<String>(MainActivity.mainContext, R.layout.item_spinner) {
            }
        spinnerAdapter.addAll(list)
        spinnerDetail.adapter = spinnerAdapter
        spinnerDetail.setSelection(0)
        spinnerDetail.dropDownVerticalOffset = dipToPixels(45f).toInt()
    }

    private fun setSpinnerLocationAdapter(): SpinnerAdapter {
        val spinnerAdapter =
            object : ArrayAdapter<String>(MainActivity.mainContext, R.layout.item_spinner) {
            }
        spinnerAdapter.addAll(items.toMutableList())
        return spinnerAdapter
    }

    private fun dipToPixels(dipValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dipValue,
            resources.displayMetrics
        )
    }

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    interface OnDialogClickListener {
        fun onClicked(name: String, facility :FacilityBody)
    }

    override fun onResume() {
        super.onResume()
        binding.btnConfirm.setOnClickListener {

            facility.data.forEach {
                Log.d("???", it.address)
            }
            val a = spinnerDetail.selectedItem.toString()
            onClickListener.onClicked(a, facility)
        }
        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }
    }
}