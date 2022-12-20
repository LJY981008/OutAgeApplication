package com.example.outageapplication.Interface

import android.util.Log
import com.example.outageapplication.BuildConfig
import com.example.outageapplication.Data.FacilityBody
import com.example.outageapplication.Data.WaterQuality
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = BuildConfig.URL_WaterQuality

interface Api {
    @GET(BuildConfig.ENDPOINT_GET_WaterQuality_STATUS)
    fun getInfo(
        @Query("pageNo") page: String?,
        @Query("year") year: String?,
        @Query("month") month: String?,
        @Query("BSI") bsi: String?,
        @Query("SIGUN") sigun: String?,
        @Query("serviceKey") key: String = BuildConfig.API_KEY,
        @Query("viewType") type: String = "json",
    ): Call<WaterQuality>
}
object RetrofitObject{
    private fun getRetrofit(): Retrofit {
        var gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiService():Api{
        return getRetrofit().create(Api::class.java)
    }
}
