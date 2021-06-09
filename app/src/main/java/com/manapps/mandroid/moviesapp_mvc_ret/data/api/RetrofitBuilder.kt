package com.manapps.mandroid.moviesapp_mvc_ret.data.api
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.harpercims.fsm.Utils.ConfigUrls
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = ConfigUrls.BASE_URL

    private   var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    /* If you want to print api responses to Log then use below client   */
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
   /* If you don't want to print api responses to Log then use below client   */
  //  private val client = OkHttpClient.Builder().build()

   

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}