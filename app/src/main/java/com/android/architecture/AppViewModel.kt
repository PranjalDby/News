package com.android.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.architecture.models.ResponseModels
import com.android.architecture.repository.Repository

class AppViewModel(var category:String,var country: String):ViewModel(){
    private var articleRepo:Repository = Repository()
    private lateinit var articleResponseLiveData:LiveData<ResponseModels>
    private lateinit var articleQueryResult:LiveData<ResponseModels>
    private lateinit var cntry: String
    fun getDashBoardLive():LiveData<ResponseModels>{
        this.cntry = country
        articleResponseLiveData = articleRepo.getDahsBoard(category = category,cntry)
        return articleResponseLiveData
    }
}