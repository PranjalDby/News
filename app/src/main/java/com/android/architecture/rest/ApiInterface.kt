package com.android.architecture.rest

import com.android.architecture.models.ResponseModels
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("apiKey")key:String,
        @Query("source")source:String,
        @Query("country")country:String,
        @Query("pageSize")pageSize:Int,
        @Query("category")category:String,
    ):Call<ResponseModels>
    @GET("everything")
    fun search(
        @Query("q")q:String,
        @Query("from")from:String="2023-01-01",
        @Query("sortBy")sortBy:String,
        @Query("apiKey")key:String,
    ):Call<ResponseModels>
}