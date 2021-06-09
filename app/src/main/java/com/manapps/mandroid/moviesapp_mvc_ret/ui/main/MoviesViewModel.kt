package com.manapps.mandroid.moviesapp_mvc_ret.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.harpercims.fsm.Utils.ErrorStatus
import com.harpercims.fsm.Utils.Resource
import com.harpercims.fsm.Utils.ResultWrapper
import com.manapps.mandroid.moviesapp_mvc_ret.data.repository.MoviesRepository
import com.manapps.mandroid.moviesapp_mvc_ret.data.models.MoviesResponse
import com.manapps.mandroid.moviesapp_mvc_ret.utils.ErrorClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<Resource<MoviesResponse>>()
    val moviesLiveData: LiveData<Resource<MoviesResponse>>
        get() = _moviesLiveData

    // val errorLiveData = MutableLiveData<List<Movies>>()
    //val errorLiveData = MutableLiveData<String>()

    // is this name appropriate?
    // is this 3 step(sendGetMoviesListRequest,setMoviesListError,handleError) approach correct?
    //how to parse all network codes data in repository and pass only relevent info to viewmodel?
    fun sendGetMoviesListRequest(apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _moviesLiveData.postValue(Resource.loading(null))
            when (val response = repository.sendGetMoviesListRequest(apiKey)) {
                is ResultWrapper.GenericError -> setMoviesListError(response.error)
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) response.value.body()?.let {
                        _moviesLiveData.postValue(Resource.success(it))
                    }
                    else {
                        when (response.value.code()) {
                            400 -> {
                                setMoviesListError(
                                    handleError(
                                        response.value.errorBody()?.string()
                                    )
                                )
                            }
                            else -> {
                                setMoviesListError(response.value.raw().message())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setMoviesListError(message: String) {
        _moviesLiveData.postValue(Resource.error(null, message))
    }

    private fun handleError(string: String?): String {
        val gson = GsonBuilder().create()
        val mError: ErrorClass?
        var message = ""
        try {
            mError = gson.fromJson(string, ErrorClass::class.java)
            if (mError != null) {
                if (mError.errors.size > 0) {
                    message = mError.errors[0]
                }
            } else {
                message = ErrorStatus.GENERAL_ERROR
            }
        } catch (a: IllegalStateException) {
            Log.e("a", a.toString())
            message = ""
        }
        return message
    }
}