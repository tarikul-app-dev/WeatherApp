package com.tarikul.weatherapp.utils

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


class CommonMethods  @Inject constructor(@ApplicationContext private val context: Context) {

        fun isOnline(): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (!(activeNetwork != null && activeNetwork.isConnectedOrConnecting)) {
                Toast.makeText(context, "You are in offline", Toast.LENGTH_LONG).show()
            }
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}