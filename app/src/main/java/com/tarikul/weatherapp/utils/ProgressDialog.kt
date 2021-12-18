package com.tarikul.weatherapp.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tarikul.weatherapp.R

class ProgressDialog (context: Context?) : Dialog(context!!){
    private lateinit var imgvLogo : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        imgvLogo = findViewById(R.id.iv_logo)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        Glide.with(context).load(R.drawable.ic_progress).into(imgvLogo)
    }
}