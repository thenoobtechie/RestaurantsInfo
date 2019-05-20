package com.example.restaurantsinfo.viewmodel.factory

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Handler
import com.example.restaurantsinfo.viewmodel.RestaurantDetailActivityViewModel

class RestaurantDetailViewModelFactory(var application: Application, var resId : String, var onResponseCallback: Handler.Callback) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return RestaurantDetailActivityViewModel(application, resId, onResponseCallback) as T
    }
}