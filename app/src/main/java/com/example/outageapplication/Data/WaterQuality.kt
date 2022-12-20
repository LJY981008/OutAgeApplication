package com.example.outageapplication.Data

import com.google.gson.annotations.SerializedName
import java.util.*

data class WaterQuality(
    @SerializedName("resultCode") val resultCode: String,      // 결과코드
    /*@SerializedName("resultMsg") val resultMsg: String,     // 결과메시지
    @SerializedName("numberOfRows") val row: Int,           // 한페이지 결과
    @SerializedName("pageNo") val pageNo: Int,              // 페이지 번호
    @SerializedName("totalCount") val totalCnt: Int,        // 총 결과 수
    @SerializedName("BRTC_NM") val brtc: String,            // 수도사업 시, 도
    @SerializedName("SIGNGU_NM") val signgu: String,        // 수도사업 시, 군, 구
    @SerializedName("COLL_DAT") val coll: Int,              // 채수일
    @SerializedName("TC") val tc: String,                   // 총대장균군
    @SerializedName("EFC") val efc: String,                 // 대장균,분원성대장균군
    @SerializedName("ODOR") val odor: String,               // 냄새
    @SerializedName("TW") val tw: String,                   // 맛
    @SerializedName("UPDATE_DAT") val update: Date          // 데이터기준일
    @SerializedName("itemsInfo") val info: List<String>,
    @SerializedName("items") val items: List<String>,
    @SerializedName("header") val result: List<String>,*/


    ){
    /*var map = mapOf<String, String>(
        "resultCode" to resultCode.toString(),
        "resultMsg" to resultMsg,
        "numberOfRows" to row.toString(),
        "pageNo" to pageNo.toString(),
        "totalCount" to totalCnt.toString(),
        "BRTC" to brtc,
        "SIGNGU" to signgu,
        "COLL_DAT" to coll.toString(),
        "TC" to tc,
        "EFC" to efc,
        "ODOR" to odor,
        "TW" to tw,
        "UPDATE" to update.toString()
    )
    fun getResult(): Map<String, String>{
        return map
    }*/
}
