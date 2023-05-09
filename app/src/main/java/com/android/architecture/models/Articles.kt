package com.android.architecture.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Articles {
    @SerializedName("source")
    @Expose
    lateinit var source:SourceModel
    @SerializedName("author")
    @Expose
    var author:String ="Iam"
    @SerializedName("title")
    @Expose
    var title:String = "Iam"
    @SerializedName("description")
    @Expose
    var description:String ="Hello"
    @SerializedName("content")
    @Expose
    var content:String ="Content"
    @SerializedName("urlToImage")
    @Expose
    var urlToImage:String = "https://pixabay.com/photos/tree-sunset-clouds-sky-silhouette-736885/"
    @SerializedName("publishedAt")
    @Expose
    lateinit var publishedAt:String
    @SerializedName("url")
    @Expose
    lateinit var url:String
    override fun toString(): String {
        return "BashBoardNews{" +
                "urlToImage=" + urlToImage + '\'' + ",description=" + description +'\'' + "}"
    }

}
