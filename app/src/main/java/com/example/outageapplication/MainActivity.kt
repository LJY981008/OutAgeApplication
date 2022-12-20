package com.example.outageapplication

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.outageapplication.FrameFragment.FacilityFragment
import com.example.outageapplication.FrameFragment.QualityFragment
import com.example.outageapplication.FrameFragment.RecordFragment
import com.example.outageapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var mainContext: MainActivity
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var isFabOpen = false
    private val tabTextList = arrayListOf("시설", "이력", "조회")
    var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainContext = this
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        if(isPermitted()) {
        }
        else {
            ActivityCompat.requestPermissions(this, permissions, 99)
        }

        viewPager.adapter = ScreenSlidePagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()



        binding.fabMain.setOnClickListener {
            toggleFab()
        }

    }

    fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun toggleFab(){
        if(isFabOpen){
            ObjectAnimator.ofFloat(binding.fabA, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabB, "translationY", 0f).apply { start() }
            isFabOpen = false;
            //binding.fabMain.setImageResource(R.drawable.) 눌렀을 때 바뀔 이미지 구하기
        }
        else{
            ObjectAnimator.ofFloat(binding.fabA, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabB, "translationY", -400f).apply { start() }
            isFabOpen = true
            //binding.fabMain.setImageResource(R.drawable.) 눌렀을 때 바뀔 이미지 구하기
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3 // 페이지 수 리턴
        override fun createFragment(position: Int): Fragment {
            return when(position){ // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> FacilityFragment()
                1 -> RecordFragment()
                else -> QualityFragment()
            }
        }
    }

}