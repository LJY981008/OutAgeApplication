package com.example.outageapplication.Interface

import com.example.outageapplication.BuildConfig
import com.example.outageapplication.Data.FacilityBody
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = BuildConfig.URL_Facility
interface APIFacility {
    @GET(BuildConfig.ENDPOINT_GET_FACILITY_STATUS)
    fun getInfo(
        @Query("perPage") perPage:Int,
        @Query("page") page:Int,
        @Query("serviceKey") serviceKey:String = BuildConfig.API_FACILITY_KEY

    ): Call<FacilityBody>
}
object RetrofitFacilityObject{
    private fun getRetrofit(): Retrofit {
        var gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiFacilityService():APIFacility{
        return getRetrofit().create(APIFacility::class.java)
    }
}