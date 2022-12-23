package com.example.outageapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginRight
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.outageapplication.FrameFragment.DroughtFragment
import com.example.outageapplication.FrameFragment.FacilityFragment
import com.example.outageapplication.FrameFragment.RecordFragment
import com.example.outageapplication.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var mainContext: MainActivity
        lateinit var fabBtn: FloatingActionButton
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val tabTextList = arrayListOf("급수시설", "단수이력", "가뭄수치")
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
        adapter.addFragment(DroughtFragment())
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false    // 스와이프 방지
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) { // 선택 X -> 선택 O
                when(tab.position){
                  2 -> {
                      fabBtn.hide()
                      fabBtn.visibility = View.GONE
                  }
                  else -> {
                      fabBtn.show()
                      fabBtn.visibility = View.VISIBLE
                  }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) { // 선택 O -> 선택
            }

            override fun onTabReselected(tab: TabLayout.Tab) { // 선택 O -> 선택 O
            }
        })
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    private fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
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