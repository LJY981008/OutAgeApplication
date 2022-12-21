package com.example.outageapplication.Util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentLoadingDialogBinding


class LoadingDialog : DialogFragment() {

    private lateinit var binding: FragmentLoadingDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingDialogBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }


}