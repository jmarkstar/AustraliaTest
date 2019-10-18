package com.jmarkstar.sampletest.extensions

import androidx.lifecycle.MutableLiveData
import com.jmarkstar.sampletest.presentation.Resource
import com.jmarkstar.sampletest.repository.FailureReason

fun <T: Any> MutableLiveData<Resource<T>>.setSuccess(data: T) = postValue(
    Resource.Success(data))

fun <T: Any> MutableLiveData<Resource<T>>.setLoading() = postValue(
    Resource.Loading)

fun <T: Any> MutableLiveData<Resource<T>>.setError(reason: FailureReason) = postValue(
    Resource.Error(reason))
