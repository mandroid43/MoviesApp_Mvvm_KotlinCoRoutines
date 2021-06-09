package com.harpercims.fsm.Utils

import android.util.Log
import com.google.gson.GsonBuilder
import com.manapps.mandroid.moviesapp_mvc_ret.utils.ErrorClass
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())

        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.GenericError(103, "Not Connected To Internet")
                is UnknownHostException -> ResultWrapper.GenericError(101, ErrorStatus.NO_CONNECTION)
                is SocketTimeoutException -> ResultWrapper.GenericError(102, ErrorStatus.TIMEOUT)
                is HttpException -> ResultWrapper.GenericError(throwable.code(), "HttpException")
                else -> {
                    ResultWrapper.GenericError(null, throwable.localizedMessage)
                }
            }
        }
    }
}


//suspend fun <T> safeApiCall(
//        dispatcher: CoroutineDispatcher = Dispatchers.IO,
//        apiCall: suspend () -> T
//): ResultWrapper<T> {
//    return withContext(dispatcher) {
//        try {
//            ResultWrapper.Success(apiCall.invoke())
//        }
//        catch (throwable: Throwable) {
//            when (throwable) {
//                is IOException -> ResultWrapper.GenericError(103, "Not Connected To Internet")
//                is UnknownHostException -> ResultWrapper.GenericError(101, ErrorStatus.NO_CONNECTION)
//                is SocketTimeoutException -> ResultWrapper.GenericError(102, ErrorStatus.TIMEOUT)
//                is HttpException -> ResultWrapper.GenericError(throwable.code(), "HttpException")
//                else -> {
//                    ResultWrapper.GenericError(null, throwable.localizedMessage)
//                }
//            }
//        }
//    }
//}


fun fetchErrorr(code: String? ): String {
var message:String=""
    val gson = GsonBuilder().create()
    val mError: ErrorClass
    try {
        mError = gson.fromJson(code, ErrorClass::class.java)
        if (mError != null) {
            if (mError.getErrors().size > 0) {
                message=mError.getErrors().get(0)
            }
        }
    }
    catch (e: IllegalStateException) {
        Log.e("e", e.toString())
    }
    return message
}


fun fetchError(code: Int): String {
    if (code in 401..403) {
        return ErrorStatus.UNAUTHORIZED
    }
    return ErrorStatus.NOT_DEFINED
}

@Suppress("UNCHECKED_CAST")
fun getMeta(meta: MutableMap<String, *>?, key: String): Int? {
    val map = meta?.get("pagination") as HashMap<String, Int>
    return map[key]
}
