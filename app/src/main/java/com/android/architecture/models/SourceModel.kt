package com.android.architecture.models

import com.google.gson.annotations.SerializedName

class SourceModel(){
    @SerializedName("id")
    var id:String ="";
    @SerializedName("name")
    lateinit var name:String
}
