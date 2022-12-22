package com.example.outageapplication

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.outageapplication.FrameFragment.FacilityFragment
import com.example.outageapplication.FrameFragment.QualityFragment
import com.example.outageapplication.FrameFragment.RecordFragment
import com.example.outageapplication.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var mainContext: MainActivity
        lateinit var fabBtn: FloatingActionButton
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
        supportActionBar?.title = "위기 대응 프로젝트"
        mainContext = this
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        fabBtn = binding.fabMain

        if(isPermitted()) {
        }
        else {
            ActivityCompat.requestPermissions(this, permissions, 99)
        }

        //viewPager.adapter = ScreenSlidePagerAdapter(this)
        val adapter = PagerFragmentStateAdapter(this)
        adapter.addFragment(FacilityFragment())
        adapter.addFragment(RecordFragment())
        adapter.addFragment(QualityFragment())
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false    // 스와이프 방지
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()



        /*binding.fabMain.setOnClickListener {
            toggleFab()
        }*/

    }

    private fun isPermitted(): Boolean {
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

    private inner class PagerFragmentStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
       var fragments : ArrayList<Fragment> = ArrayList()

       override fun getItemCount(): Int {
           return fragments.size
       }

       override fun createFragment(position: Int): Fragment {
           return fragments[position]
       }

       fun addFragment(fragment: Fragment){
           fragments.add(fragment)
           notifyItemInserted(fragments.size - 1)
       }

       fun removeFragment(){
           fragments.removeLast()
           notifyItemRemoved(fragments.size)
       }
   }
}