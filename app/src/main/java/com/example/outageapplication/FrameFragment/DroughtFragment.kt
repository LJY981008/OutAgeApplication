package com.example.outageapplication.FrameFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.outageapplication.Adapter.SearchAdapter
import com.example.outageapplication.Data.DroughtBody
import com.example.outageapplication.Data.DroughtItem
import com.example.outageapplication.Data.FacilityBody
import com.example.outageapplication.Interface.RetrofitDroughtObject
import com.example.outageapplication.Interface.RetrofitFacilityObject
import com.example.outageapplication.MainActivity
import com.example.outageapplication.databinding.FragmentDroughtBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DroughtFragment : Fragment() {
    private lateinit var binding: FragmentDroughtBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var rvDought: RecyclerView
    private var list = mutableListOf<DroughtItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDroughtBinding.inflate(inflater, container, false)
        MainActivity.fabBtn.visibility = View.GONE
        rvDought = binding.rvDought
        adapter = SearchAdapter(this.requireContext())
        binding.rvDought.adapter = adapter
        getRetro()
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do Nothing
            }

            override fun onTextChanged(charS: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filter?.filter(charS)
            }

            override fun afterTextChanged(p0: Editable?) {
                //Do Nothing
            }

        })
        getRetro()

        return binding.root
    }


    private fun getRetro() {
        RetrofitDroughtObject.getApiDroughtService().getInfo(1, 167)
            .enqueue(object : Callback<DroughtBody> {
                override fun onResponse(
                    call: Call<DroughtBody>,
                    response: Response<DroughtBody>
                ) {
                    Log.d("성공", response.code().toString())
                    response.body()?.data?.forEach{
                        list.add(DroughtItem(it.getDroughtMap()))
                    }
                    adapter.datas = list
                    adapter.saveDatas = list
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<DroughtBody>, t: Throwable) {
                    Log.d("실패", t.message.toString())
                }

            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.fabBtn.visibility = View.VISIBLE
    }

}