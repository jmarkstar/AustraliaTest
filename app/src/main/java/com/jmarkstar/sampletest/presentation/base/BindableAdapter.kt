package com.jmarkstar.sampletest.presentation.base

interface BindableAdapter<T> {
    fun setData(items: List<T>)
}