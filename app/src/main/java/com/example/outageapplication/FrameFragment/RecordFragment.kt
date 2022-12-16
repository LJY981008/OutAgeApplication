package com.example.outageapplication.FrameFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)

        return binding.root
    }

}