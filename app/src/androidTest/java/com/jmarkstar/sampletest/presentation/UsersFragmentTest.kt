package com.jmarkstar.sampletest.presentation

import android.content.Intent
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jmarkstar.sampletest.presentation.common.CoroutineContextProvider
import com.jmarkstar.sampletest.presentation.users.UsersViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.*

@LargeTest
class UsersFragmentTest {

    @get:Rule var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    //@get:Rule val databindingIdlingResourceRule = DataBindingIdlingResourceRule(activityTestRule)

    private val mockViewModel: UsersViewModel = mock(UsersViewModel::class.java)

    @Before fun setup() {

        loadKoinModules(module {
            single { CoroutineContextProvider() }
            viewModel{ mockViewModel }
        })

        activityTestRule.launchActivity(Intent())
    }

    @After fun unsetup() {
        stopKoin()
    }

    @Test
    fun test() {

    }
}

/**

// Create a mock NavController
val mockNavController = mock(NavController::class.java)

// Create a graphical FragmentScenario for the TitleScreen
val titleScenario = launchFragmentInContainer<UsersFragment>()

// Set the NavController property on the fragment
titleScenario.onFragment { fragment ->
Navigation.setViewNavController(fragment.requireView(), mockNavController)
}

 *
 * */

