package com.redveloper.news.domain.repository

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val message: String): Result<Nothing>()
}