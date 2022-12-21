package com.example.outageapplication.Dialog

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentMakerInfoBinding


class MarkerInfoDialog : DialogFragment() {
    private lateinit var binding: FragmentMakerInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakerInfoBinding.inflate(inflater, container, false)
        val bundle = arguments
        binding.tvName.text = bundle!!.getString("Name")
        binding.tvDivision.text = bundle!!.getString("Division")
        binding.tvScale.text = bundle!!.getString("Scale")
        binding.tvAddress.text = bundle!!.getString("Address")
        binding.tvDistance.text = bundle!!.getInt("Distance").toString() +"m"
        binding.btnConfirm.setOnClickListener {
            this.dismiss()
        }
        return binding.root
    }
}