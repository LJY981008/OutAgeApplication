package com.example.outageapplication.FrameFragment

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.outageapplication.Data.FacilityBody
import com.example.outageapplication.Interface.RetrofitFacilityObject
import com.example.outageapplication.MainActivity
import com.example.outageapplication.Util.LoadingDialog
import com.example.outageapplication.Util.SelectDialog
import com.example.outageapplication.databinding.FragmentFacilityBinding
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
    private var markers:MutableList<Marker> = arrayListOf()


    private lateinit var binding: FragmentFacilityBinding
    private lateinit var mapView: MapView
    private var mapData: MutableList<Map<String, String>> = arrayListOf()
    private lateinit var locationSource: FusedLocationSource
    private var geocoder = Geocoder(MainActivity.mainContext)
    private val loadingDialog = LoadingDialog()
    private val selectDialog= SelectDialog()
    //private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFacilityBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        //locationSource = FusedLocationSource(this, 1000)

        loadingDialog.show(this.childFragmentManager, "loading")
        getMapFacilityData()

        MainActivity.fabBtn.setOnClickListener {
            selectDialog.show(this.childFragmentManager, "select")
        }

        return binding.root
    }

    /**
     * 급수시설 호출
     */
    private fun getMapFacilityData() {
        Log.d("함수", "호출")
        RetrofitFacilityObject.getApiFacilityService().getInfo(10, 1)
            .enqueue(object : Callback<FacilityBody> {
                override fun onResponse(
                    call: Call<FacilityBody>,
                    response: Response<FacilityBody>
                ) {
                    Log.d("성공", response.message() + response.code())
                    if (response.code() == 200) {
                        setMap(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<FacilityBody>, t: Throwable) {
                    Log.d("실패", t.message.toString())
                }

            })
    }

    /**
     * 호출한 데이터 지도에 적용
     */
    private fun setMap(content: FacilityBody) {


        content.data.forEach {
            mapData.add(it.getFacilityMap())
            var trans = transAddress(it.address)
            val tmpMarker = Marker()
            tmpMarker.position = LatLng(trans.latitude, trans.longitude)
            tmpMarker.map = naverMap
            tmpMarker.icon = MarkerIcons.BLUE
            tmpMarker.iconTintColor = Color.BLUE
            markers.add(tmpMarker)
        }

        loadingDialog.dismiss()

    }

    fun transAddress(address: String): Address {
        val cor = geocoder.getFromLocationName(address, 1)
        return cor[0]
    }


    override fun onMapReady(nMap: NaverMap) {
        naverMap = nMap
        var standardLatitude = 37.5125            //기준경도
        var standardLongitude = 127.102778        //기준위도
        var camPos = CameraPosition(
            LatLng(standardLatitude, standardLongitude),
            10.0
        )
        naverMap.cameraPosition = camPos
        // 기준위치에 마커 표시
        marker.position = LatLng(standardLatitude, standardLongitude)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED

        // 현재 내위치에 마커 찍는 기능
        /*fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.mainContext) //gps 자동으로 받아오기
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
            addOnLocationChangeListener() //위치변경
        }*/
    }
}