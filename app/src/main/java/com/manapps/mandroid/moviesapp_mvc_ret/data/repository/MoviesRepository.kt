package com.manapps.mandroid.moviesapp_mvc_ret.data.repository
import com.harpercims.fsm.Utils.Resource
import com.harpercims.fsm.Utils.safeApiCall
import com.manapps.mandroid.moviesapp_mvc_ret.data.api.ApiService

class MoviesRepository(private val apiService: ApiService) {
    suspend fun sendGetMoviesListRequest(apiKey: String) = safeApiCall { apiService.sendGetMoviesListRequest(apiKey)

//        if (response.value.isSuccessful) response.value.body()?.let {
//            _moviesLiveData.postValue(Resource.success(it))
//        }
//        else {
//            when (response.value.code()) {
//                400 -> {
//                    setMoviesListError(
//                        handleError(
//                            response.value.errorBody()?.string()
//                        )
//                    )
//                }
//                else -> {
//                    setMoviesListError(response.value.raw().message())
//                }
//            }
//        }

    }

}