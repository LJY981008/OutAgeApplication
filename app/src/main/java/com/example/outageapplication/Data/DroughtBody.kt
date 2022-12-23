package com.example.outageapplication.Data

import com.google.gson.annotations.SerializedName
import java.util.*

data class DroughtBody(
    @SerializedName("page") val page: Int,
    @SerializedName("perPage") val perPage: Int,
    @SerializedName("totalCount") val total: Int,
    @SerializedName("currentCount") val current: Int,
    @SerializedName("matchCount") val match: Int,
    @SerializedName("data") val data: List<Drought>
) {
}

data class Drought(
    @SerializedName("시도") val local: String,
    @SerializedName("시군") val localDetail: String,
    @SerializedName("시설개수") val numFacility: Int,
    @SerializedName("유역면적(ha)") val basin: String,
    @SerializedName("수혜면적(ha)") val beneficiary: String,
    @SerializedName("유효저수량(ha)") val effective: String,
    @SerializedName("저수율") val waterYield: String,
    @SerializedName("평년") val ave: String,
    @SerializedName("대비") val prepare: String,
    @SerializedName("단계") val step: String,

    ) {
    fun getDroughtMap(): Map<String, String> {
        return mapOf(
            "local" to "$local($localDetail)",
            "facility" to "시설: ${numFacility}개",
            "yield" to "저수율:$waterYield",
            "ave" to "평년:$ave",
            "prepare" to "대비:$prepare",
            "step" to "단계:$step"
        )
    }
}
