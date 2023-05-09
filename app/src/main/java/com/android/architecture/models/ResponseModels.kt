package com.android.architecture.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ResponseModels(){
    @SerializedName("status")
    @Expose
    lateinit var status:String
    @SerializedName("totalResults")
    @Expose
    var totalResult:Int = 0
    @SerializedName("url")
    @Expose
    var url:String = ""
    @SerializedName("articles")
    @Expose
    var articles = listOf<Articles>()
    override fun toString(): String {
        return "BashBoardNewsResponse{" +
        "articles=" + this.articles + "}"
    }
}