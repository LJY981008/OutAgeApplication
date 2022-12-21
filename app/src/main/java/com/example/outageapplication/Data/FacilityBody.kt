package com.example.outageapplication.Data

import com.google.gson.annotations.SerializedName

data class FacilityBody(
    @SerializedName("page") val page :Int,
    @SerializedName("perPage") val perPage: Int,
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("currentCount") val currentCount: Int,
    @SerializedName("matchCount") val matchCount: Int,
    @SerializedName("data") val data:List<Facility>
){}

data class Facility(
    @SerializedName("규모") val scale:String,
    @SerializedName("데이터 기준 일자") val date:String,
    @SerializedName("수질등급") val quality:String,
    @SerializedName("시설구분") val division:String,
    @SerializedName("비상시설관리번호") val num:String,
    @SerializedName("읍면동") val dong:String,
    @SerializedName("시설도로명주소") val address:String,
    @SerializedName("시설명") val buildName:String,
    @SerializedName("준공일자") val completionDate:String,
    @SerializedName("지정일자") val appointedDate:String,
){

    fun getFacilityMap(): Map<String, String> {
        return mapOf(
            "Scale" to scale,
            "Date" to date,
            "Quality" to quality,
            "Division" to division,
            "Num" to num,
            "Dong" to dong,
            "Address" to address,
            "BuildName" to buildName,
            "CompletionDate" to completionDate,
            "AppointedDate" to appointedDate
        )
    }
}