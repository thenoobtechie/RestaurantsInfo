package com.example.restaurantsinfo.viewmodel.factory

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Handler
import com.example.restaurantsinfo.viewmodel.MainActivityViewModel

//For parameterized viewmodel
class MainViewModelFactory(var application : Application, var callback : Handler.Callback) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MainActivityViewModel(application, callback) as T
    }
}