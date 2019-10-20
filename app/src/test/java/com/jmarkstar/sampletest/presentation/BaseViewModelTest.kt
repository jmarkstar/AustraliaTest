package com.jmarkstar.sampletest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jmarkstar.sampletest.presentation.common.TestCoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    protected val testCoroutineConextProvider = TestCoroutineContextProvider()
}