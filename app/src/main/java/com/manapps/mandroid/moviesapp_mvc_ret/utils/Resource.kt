package com.harpercims.fsm.Utils

data class Resource<out T>(val status: ResultStatus, val data: T?, val message: String?) {



    companion object {
        //    fun <T> success(data: T?): Resource<T> {
//        return Resource(Status.SUCCESS, data, null)
//    }
        fun <T> success(data: T?): Resource<T> = Resource(ResultStatus.SUCCESS, data, null)

        fun <T> error(data: T?, message: String?) = Resource(ResultStatus.ERROR, data, message)

        fun <T> loading(data: T?) = Resource(ResultStatus.LOADING, data, null)
    }

}