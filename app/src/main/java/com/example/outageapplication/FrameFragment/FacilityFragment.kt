package com.example.outageapplication.FrameFragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.outageapplication.Data.FacilityBody
import com.example.outageapplication.Interface.RetrofitFacilityObject
import com.example.outageapplication.MainActivity
import com.example.outageapplication.R
import com.example.outageapplication.databinding.FragmentFacilityBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FacilityFragment : Fragment(), OnMapReadyCallback {
    companion object {
        lateinit var naverMap: NaverMap
    }

    private val marker = Marker()
    private val marker1 = Marker()
    private val marker2 = Marker()


    private lateinit var binding: FragmentFacilityBinding
    private lateinit var mapView: MapView
    private lateinit var mapData: MutableList<Map<String, String>>
    private lateinit var locationSource: FusedLocationSource
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFacilityBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, 1000)

        getMapFacilityData()
        return binding.root
    }

    /**
     * 급수시설 호출
     */
    private fun getMapFacilityData() {
        Log.d("함수", "호출")
        RetrofitFacilityObject.getApiFacilityService().getInfo(1, 10)
            .enqueue(object : Callback<FacilityBody> {
                override fun onResponse(
                    call: Call<FacilityBody>,
                    response: Response<FacilityBody>
                ) {
                    Log.d("성공", response.message() + response.code())
                    setMap(response.code(), response.body()!!)
                }

                override fun onFailure(call: Call<FacilityBody>, t: Throwable) {
                    Log.d("실패", t.message.toString())
                }

            })
    }

    /**
     * 호출한 데이터 지도에 적용
     */
    private fun setMap(code: Int, context: FacilityBody) {

        context.data[0].getFacilityMap()
        /*for(i:Int in 0..n!!){
            Log.d("포문", i.toString())
            //mapData.add(context.data[i].getFacilityMap())
            mapData[i]["map"]?.let {
                Log.d("${i}번", it)
            }
        }*/
    }

    override fun onMapReady(nMap: NaverMap) {
        naverMap = nMap
        var camPos = CameraPosition(
            LatLng(34.38, 128.55),
            20.0
        )
        naverMap.cameraPosition = camPos
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.mainContext) //gps 자동으로 받아오기
        if (ActivityCompat.checkSelfPermission(
                MainActivity.mainContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.mainContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location : Location? ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                naverMap.locationSource = locationSource
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                marker.position = LatLng(latitude, longitude)
                marker.map = naverMap
                marker.icon = MarkerIcons.BLACK
                marker.iconTintColor = Color.RED
            }
        }
    }



}