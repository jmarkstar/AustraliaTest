package com.jmarkstar.sampletest.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(protected val coroutineContextProvider: CoroutineContextProvider): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + coroutineContextProvider.Main

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}