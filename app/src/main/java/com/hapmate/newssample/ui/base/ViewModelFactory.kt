package com.hapmate.newssample.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hapmate.newssample.data.api.ApiService
import com.hapmate.newssample.data.repository.MainRepository
import com.hapmate.newssample.ui.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}