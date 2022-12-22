package com.example.outageapplication.Data

data class RecordItem(
    var data: Map<String, String>
){
    fun getStartDate(): String {
        return data["startDate"]!!
    }
}
