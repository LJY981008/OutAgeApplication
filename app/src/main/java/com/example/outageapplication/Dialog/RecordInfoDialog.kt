package com.example.outageapplication.Dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentRecordInfoDialogBinding

class RecordInfoDialog : DialogFragment() {
    private lateinit var binding: FragmentRecordInfoDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordInfoDialogBinding.inflate(inflater, container, false)
        val bundle = arguments
        binding.tvCause.text = bundle!!.getString("Cause")
        binding.tvStartDate.text = bundle!!.getString("Start")
        binding.tvEndDate.text = bundle!!.getString("End")
        binding.tvAddress.text = bundle!!.getString("Address")
        binding.tvCorporationContents.text = bundle!!.getString("Contents")
        binding.btnConfirm.setOnClickListener {
            this.dismiss()
        }
        return binding.root
    }

}