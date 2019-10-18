package com.jmarkstar.sampletest.repository.network

import com.jmarkstar.sampletest.repository.FailureReason
import java.lang.Exception
import java.net.UnknownHostException
import java.net.UnknownServiceException
import com.jmarkstar.sampletest.repository.Result

suspend fun <T: Any>processNetworkResult(resultCode: Int, sucessResult: suspend () -> Result<T>): Result<T> {
    return when(resultCode){
        in 200..204 -> {

            sucessResult.invoke()
        }
        400 -> Result.Failure(FailureReason.WRONG_VALUES_ON_PARAMETERS)
        401 -> Result.Failure(FailureReason.EXPIRED_TOKEN)
        else -> {
            Result.Failure(FailureReason.INTERNAL_ERROR)
        }
    }
}

fun <T: Any>processError(exception: Exception): Result<T> {
    exception.printStackTrace()
    return when(exception){
        is UnknownHostException -> Result.Failure(FailureReason.SERVER_COULDNT_BE_FOUND)
        is UnknownServiceException -> Result.Failure(FailureReason.SERVER_COULDNT_BE_FOUND)
        else -> Result.Failure(FailureReason.INTERNAL_ERROR)
    }
}