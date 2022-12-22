package com.example.outageapplication.Interface

import com.example.outageapplication.BuildConfig
import com.example.outageapplication.Data.FacilityBody
import com.example.outageapplication.Data.RecordBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = BuildConfig.URL_Record
interface APIRecord {
    @GET(BuildConfig.ENDPOINT_GET_RECORD_STATUS)
    fun getInfo(
        @Query("perPage") perPage:Int,
        @Query("page") page:Int,
        @Query("serviceKey") serviceKey:String = BuildConfig.API_RECORD_KEY
    ): Call<RecordBody>
}
object RetrofitRecordObject{
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiFacilityService():APIRecord{
        return getRetrofit().create(APIRecord::class.java)
    }
}