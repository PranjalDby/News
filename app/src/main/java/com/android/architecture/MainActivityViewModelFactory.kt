package com.android.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.architecture.repository.Repository

class MainActivityViewModelFactory(var category:String,var country: String):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AppViewModel::class.java)){
            return  AppViewModel(category, country = country) as T
        }
        throw java.lang.IllegalArgumentException("Unknown Model Class")
    }
}