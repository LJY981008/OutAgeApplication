package com.example.outageapplication.Util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import com.example.outageapplication.R

class LoadingDialog
constructor(context: Context) : Dialog(context) {
        init{
            setCancelable(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_loading)
            Log.d("다이","얼로그")
        }
}