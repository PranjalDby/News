package com.android.architecture.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.architecture.Apikeys
import com.android.architecture.models.ResponseModels
import com.android.architecture.rest.ApiClient
import com.android.architecture.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class Repository() {
     private var request:ApiInterface = ApiClient().getclient().create(ApiInterface::class.java)
    final val KEY = Apikeys().getKeys();
     fun getDahsBoard(category:String,country:String):LiveData<ResponseModels>{
        val data = MutableLiveData<ResponseModels>()
        request.getTopHeadlines(key = KEY,"TechCrunch",country,100,category)
            .enqueue(object : Callback<ResponseModels>{
                override fun onResponse(
                    call: Call<ResponseModels>,
                    response: Response<ResponseModels>
                ) {
                    if(response.body()!=null){
                        data.value = response.body()
                    }
                }
                override fun onFailure(call: Call<ResponseModels>, t: Throwable) {
                    Log.e("Error In Repo",t.message.toString())
                }
            })
        return data
    }
}
fun main(){
    var json:GsonConverterFactory = GsonConverterFactory.create()
    print(Repository().KEY)
}
