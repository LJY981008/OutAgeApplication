package com.example.outageapplication

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.outageapplication.FrameFragment.FacilityFragment
import com.example.outageapplication.FrameFragment.QualityFragment
import com.example.outageapplication.FrameFragment.RecordFragment
import com.example.outageapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var isFabOpen = false
    val tabTextList = arrayListOf("시설", "이력", "조회")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        viewPager.adapter = ScreenSlidePagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        binding.fabMain.setOnClickListener {
            toggleFab()
        }
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












    override fun onBackPressed() {
        if(viewPager.currentItem ==0){
            super.onBackPressed()
        }
        else{
            viewPager.currentItem = viewPager.currentItem - 1
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