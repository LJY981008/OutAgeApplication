package com.example.outageapplication.Data

import com.google.gson.annotations.SerializedName

data class RecordBody(
    @SerializedName("page") val page :Int,
    @SerializedName("perPage") val perPage: Int,
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("currentCount") val currentCount: Int,
    @SerializedName("matchCount") val matchCount: Int,
    @SerializedName("data") val data:List<Record>
){}

data class Record(
    @SerializedName("번호") val num:Int,
    @SerializedName("지자체명") val local:String,
    @SerializedName("단수지역") val address:String,
    @SerializedName("단수사유") val cause:String,
    @SerializedName("단수시작일시") val startDate:String,
    @SerializedName("단수시작시간") val startTime:String,
    @SerializedName("단수종료일시") val endDate:String,
    @SerializedName("단수종료시간") val endTime:String,
    @SerializedName("공사명") val corporation:String,
    @SerializedName("공사개요") val corporationContents:String,
    @SerializedName("작성부서") val writeDepart:String,
    @SerializedName("작성일") val writeDate:String,
    @SerializedName("담당자") val manager:String,
    @SerializedName("연락처") val contact:String,
){

    fun getRecordMap(): Map<String, String> {
        return mapOf(
            "local" to local,
            "address" to address,
            "cause" to cause,
            "startDate" to startDate,
            "startTime" to startTime,
            "endDate" to endDate,
            "endTime" to endTime,
            "corporation" to corporation
        )
    }
}