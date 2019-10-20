package com.jmarkstar.sampletest.presentation.common

interface BindableAdapter<T> {
    fun setData(items: List<T>)
}