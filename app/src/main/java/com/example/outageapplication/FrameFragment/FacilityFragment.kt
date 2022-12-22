package com.example.outageapplication.FrameFragment

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.outageapplication.Data.FacilityBody
import com.example.outageapplication.Interface.RetrofitFacilityObject
import com.example.outageapplication.MainActivity
import com.example.outageapplication.Util.DistanceManager
import com.example.outageapplication.Dialog.LoadingDialog
import com.example.outageapplication.Dialog.MarkerInfoDialog
import com.example.outageapplication.Dialog.SelectFacilityDialog
import com.example.outageapplication.databinding.FragmentFacilityBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
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
    private var markers: MutableList<Marker> = arrayListOf()
    private var standardLatitude = 37.5125            //기준경도
    private var standardLongitude = 127.102778        //기준위도

    private lateinit var binding: FragmentFacilityBinding
    private lateinit var mapView: MapView
    private var mapData: MutableList<Map<String, String>> = arrayListOf()
    private var geocoder = Geocoder(MainActivity.mainContext)
    private val loadingDialog = LoadingDialog()
    private val selectFacilityDialog = SelectFacilityDialog()
    private val markerInfoDialog = MarkerInfoDialog()
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
        //loadingDialog.show(this.childFragmentManager, "loading")


        return binding.root
    }


    /**
     * 호출한 데이터 지도에 적용
     */
    private fun setMap(location: String, content: FacilityBody) {

        content.data.forEach {
            try {
                if (it.getDetailLocation() == location) {
                    Log.d("이프", it.address)
                    mapData.add(it.getFacilityMap())
                    var trans = transAddress(it.address)
                    var distance =
                        DistanceManager.getDistance(
                            standardLatitude,
                            standardLongitude,
                            trans.latitude,
                            trans.longitude
                        )
                    val tmpMarker = Marker()
                    tmpMarker.isIconPerspectiveEnabled = true
                    tmpMarker.position = LatLng(trans.latitude, trans.longitude)
                    tmpMarker.map = naverMap
                    tmpMarker.icon = MarkerIcons.BLUE
                    tmpMarker.iconTintColor = Color.BLUE
                    tmpMarker.onClickListener = Overlay.OnClickListener { _ ->
                        val bundle = Bundle()
                        bundle.putString("Name", it.getFacilityMap()["BuildName"])
                        bundle.putString("Division", it.getFacilityMap()["Division"])
                        bundle.putString("Scale", it.getFacilityMap()["Scale"])
                        bundle.putString("Address", it.getFacilityMap()["Address"])
                        bundle.putInt("Distance", distance)
                        markerInfoDialog.arguments = bundle
                        markerInfoDialog.show(this.childFragmentManager, "info")
                        false
                    }
                    markers.add(tmpMarker)
                }
            } catch (e: Exception) {
                Log.d("익셉", e.message.toString())
            }

        }
    }

    /**
     * 주소 경위도로 변환
     */
    private fun transAddress(address: String): Address {
        val cor = geocoder.getFromLocationName(address, 1)
        return cor[0]
    }

    /**
     * 기준 위치에 마커표시
     * GPS위치로 사용 예정
     */
    override fun onMapReady(nMap: NaverMap) {
        naverMap = nMap
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.uiSettings.isScaleBarEnabled = false
        naverMap.uiSettings.isLogoClickEnabled = false

        var camPos = CameraPosition(
            LatLng(standardLatitude, standardLongitude),
            10.0
        )
        naverMap.cameraPosition = camPos
        // 기준위치에 마커 표시
        /*marker.position = LatLng(standardLatitude, standardLongitude)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED*/

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

    override fun onResume() {
        super.onResume()
        //loadingDialog.show(childFragmentManager, "loading")
        MainActivity.fabBtn.setOnClickListener {
            selectFacilityDialog.show(this.childFragmentManager, "select")
            selectFacilityDialog.setOnClickListener(object :
                SelectFacilityDialog.OnDialogClickListener {
                override fun onClicked(location: String, facility: FacilityBody) {
                    selectFacilityDialog.dismiss()
                    markers.forEach {
                        it.map = null
                    }
                    markers.clear()

                    setMap(location, facility)
                }

            })
        }
    }
}