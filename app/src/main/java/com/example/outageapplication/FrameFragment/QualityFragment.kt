package com.example.outageapplication.FrameFragment

import android.net.Network
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.outageapplication.BuildConfig
import com.example.outageapplication.Data.Facility
import com.example.outageapplication.Data.FacilityBody
import com.example.outageapplication.Data.WaterQuality
import com.example.outageapplication.Interface.RetrofitFacilityObject
import com.example.outageapplication.Interface.RetrofitObject
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentQualityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QualityFragment : Fragment() {
    private lateinit var binding: FragmentQualityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQualityBinding.inflate(inflater, container, false)
        binding.btn.setOnClickListener {
            //getRetro()
        }
        return binding.root
    }


    private fun getRetro(){
        RetrofitFacilityObject.getApiFacilityService().getInfo(1,100)
            .enqueue(object : Callback<FacilityBody>{
                override fun onResponse(
                    call: Call<FacilityBody>,
                    response: Response<FacilityBody>
                ) {
                    Log.d("성공",response.message() + response.code())
                }

                override fun onFailure(call: Call<FacilityBody>, t: Throwable) {
                    Log.d("실패",t.message.toString())
                }

            })
    }





    private fun getWaterQualityStatus(){
        RetrofitObject.getApiService().getInfo("1", "2018", "1", "경기도", "의정부시")
            .enqueue(object  : Callback<WaterQuality>{
                override fun onResponse(
                    call: Call<WaterQuality>,
                    response: Response<WaterQuality>
                ) {
                    //setText(response.code(), response.body())
                    Log.d("message",response.message())
                }

                override fun onFailure(call: Call<WaterQuality>, t: Throwable) {
                    Log.d("Failed","${t.message}" )
                }

            })
    }

}