package com.jmarkstar.sampletest.repository

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    data class Failure(val reason: FailureReason) : Result<Nothing>()
}

enum class FailureReason {

    //common errors
    DATABASE_OPERATION_ERROR,
    EXPIRED_TOKEN,
    INTERNAL_ERROR,
    SERVER_COULDNT_BE_FOUND,
    WRONG_VALUES_ON_PARAMETERS
}