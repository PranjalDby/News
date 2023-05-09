package com.android.architecture.rest

import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

open class ApiClient(){
    final val BASE_URL = "https://newsapi.org/v2/"
    var retrofit: Retrofit? = null
    fun getclient():Retrofit{
        if(retrofit == null){
            retrofit = retrofit2.Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}