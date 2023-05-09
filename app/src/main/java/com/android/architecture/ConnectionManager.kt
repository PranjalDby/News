package com.android.architecture

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pManager.NetworkInfoListener
import android.widget.Toast

class ConnectionManager: BroadcastReceiver() {
    var st:Boolean?=null
    override fun onReceive(context: Context?, intent: Intent?) {
        var status = NetworkUtil().getConnectivityStatusString(context!!)
        if(status == false){
            st = false
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
        else{
            st = true
        }

    }
    fun returnst():Boolean{
        return st!!
    }
}