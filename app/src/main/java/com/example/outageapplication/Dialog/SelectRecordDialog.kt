package com.example.outageapplication.Dialog

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.outageapplication.FrameFragment.RecordFragment
import com.example.outageapplication.MainActivity
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentSelectRecordDialogBinding

class SelectRecordDialog : DialogFragment() {
    private lateinit var binding: FragmentSelectRecordDialogBinding
    private lateinit var spinner: Spinner
    private lateinit var localList: ArrayList<String>
    private lateinit var onClickListener: OnDialogClickListener
    private var isRecent = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectRecordDialogBinding.inflate(inflater, container, false)

        val bundle = arguments
        localList = bundle!!.getStringArrayList("localList") as ArrayList<String>

        setRadioListener()
        
        spinner = binding.spinner
        spinner.adapter = setSpinnerAdapter()
        spinner.setSelection(0)
        spinner.dropDownVerticalOffset = dipToPixels(45f).toInt()
        setSelectedListener()
        
        binding.btnConfirm.setOnClickListener {
            onClickListener.onClicked(spinner.selectedItem as String, isRecent)
            this.dismiss()
        }
        binding.btnCancel.setOnClickListener {

            this.dismiss()
        }

        return binding.root
    }

    /**
     * 라디오버튼 선택 이벤트
     */
    private fun setRadioListener() {
        binding.radioGroup.check(binding.radioRecent.id)
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.radioRecent.id -> {
                    isRecent = true
                }
                binding.radioOld.id -> {
                    isRecent = false
                }
                else -> {}
            }

        }
    }
    /**
     * 스피너 선택 이벤트
     */
    private fun setSelectedListener() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                    }
                    1 -> {
                    }
                    2 -> {
                    }
                    else -> {
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                spinner.setSelection(0)
            }
        }
    }

    /**
     * 스피너 어뎁터
     */
    private fun setSpinnerAdapter(): SpinnerAdapter {
        val spinnerAdapter =
            object : ArrayAdapter<String>(MainActivity.mainContext, R.layout.item_spinner) {

                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                    val v = super.getView(position, convertView, parent)

                    if (position == count) {
                        //마지막 포지션의 textView 를 힌트 용으로 사용합니다.
                        (v.findViewById<View>(R.id.tvItemSpinner) as TextView).text = ""
                        //아이템의 마지막 값을 불러와 hint로 추가해 줍니다.
                        (v.findViewById<View>(R.id.tvItemSpinner) as TextView).hint = getItem(count)
                    }

                    return v
                }

                override fun getCount(): Int {
                    //마지막 아이템은 힌트용으로만 사용하기 때문에 getCount에 1을 빼줍니다.
                    return super.getCount() - 1
                }

            }
        spinnerAdapter.addAll(localList.toMutableList())
        spinnerAdapter.add("지역 선택")
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
        fun onClicked(name: String, isRecent: Boolean)
    }
}