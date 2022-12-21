package com.example.outageapplication.Dialog

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.outageapplication.MainActivity
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentSelectDialogBinding

class SelectDialog : DialogFragment() {
    private lateinit var binding: FragmentSelectDialogBinding
    private lateinit var spinner: Spinner
    private var items = arrayOf(
        "송파구",
        "1",
        "2"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectDialogBinding.inflate(inflater, container, false)
        spinner = binding.spinner

        spinner.adapter = setSpinnerAdapter()
        spinner.setSelection(0)
        spinner.dropDownVerticalOffset = dipToPixels(45f).toInt()
        setSelectedListener()

        binding.btnConfirm.setOnClickListener {

        }
        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }

    private fun setSelectedListener(){
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
        spinnerAdapter.addAll(items.toMutableList())
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
}