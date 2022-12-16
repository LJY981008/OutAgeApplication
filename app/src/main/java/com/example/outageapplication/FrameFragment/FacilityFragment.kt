package com.example.outageapplication.FrameFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentFacilityBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FacilityFragment : Fragment() {

    private lateinit var binding: FragmentFacilityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFacilityBinding.inflate(inflater, container, false)

        return binding.root
    }


}