package com.arctouch.codechallenge.home

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.api.TmdbApi

class HomeViewModelFactory (private val api: TmdbApi)
    : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(api) as T
    }
}