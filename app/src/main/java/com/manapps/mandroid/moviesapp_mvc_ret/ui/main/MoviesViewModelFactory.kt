package com.manapps.mandroid.moviesapp_mvc_ret.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manapps.mandroid.moviesapp_mvc_ret.R
import com.manapps.mandroid.moviesapp_mvc_ret.data.api.ApiService
import com.manapps.mandroid.moviesapp_mvc_ret.data.repository.MoviesRepository
import java.lang.IllegalArgumentException

class MoviesViewModelFactory(private val apiService: ApiService, context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(MoviesRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}