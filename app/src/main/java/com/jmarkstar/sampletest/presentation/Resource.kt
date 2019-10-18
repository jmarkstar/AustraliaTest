package com.jmarkstar.sampletest.presentation

import com.jmarkstar.sampletest.repository.FailureReason

sealed class Resource<out T : Any> {
    object Loading : Resource<Nothing>()
    data class Success<out T : Any>(val value: T) : Resource<T>()
    object Empty : Resource<Nothing>()
    data class Error(val reason: FailureReason) : Resource<Nothing>()
}