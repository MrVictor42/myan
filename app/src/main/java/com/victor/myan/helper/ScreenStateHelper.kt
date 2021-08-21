package com.victor.myan.helper

sealed class ScreenStateHelper<T> (val data : T? = null, val message : String? = null) {
    class Success<T>(data : T?) : ScreenStateHelper<T>(data)
    class Loading<T>(data : T?) : ScreenStateHelper<T>(data)
    class Error<T>(message: String, data : T?) : ScreenStateHelper<T>(data, message)
}