package com.application.myapplication.util


sealed class Resource<out R> {
    data class Success<out R>(val data: R): Resource<R>()
    data class Failure<R>(val exception: R): Resource<Nothing>()
    object Loading: Resource<Nothing>()
    object Idle: Resource<Nothing>()
}

//
//sealed class Resource<out T> {
//    data class Success<out T>(val data: T) : Resource<T>()
//    data class Error(val exception: Exception) : Resource<Nothing>()
//    object Loading : Resource<Nothing>()
//    object Idle : Resource<Nothing>()
//}
