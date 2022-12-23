package com.example.outageapplication.Interface

import com.example.outageapplication.BuildConfig
import com.example.outageapplication.Data.DroughtBody
import com.example.outageapplication.Data.FacilityBody
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = BuildConfig.URL_DROUGHT

interface APIDrought {
    @GET(BuildConfig.ENDPOINT_GET_DROUGHT_STATUS)
    fun getInfo(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = BuildConfig.API_DROUGHT_KEY,
    ): Call<DroughtBody>
}
object RetrofitDroughtObject{
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiDroughtService():APIDrought{
        return getRetrofit().create(APIDrought::class.java)
    }
}