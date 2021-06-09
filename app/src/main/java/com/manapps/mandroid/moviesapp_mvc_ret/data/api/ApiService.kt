package com.manapps.mandroid.moviesapp_mvc_ret.data.api

import com.manapps.mandroid.moviesapp_mvc_ret.data.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
   suspend fun sendGetMoviesListRequest(@Query("api_key") key: String): Response<MoviesResponse>
}