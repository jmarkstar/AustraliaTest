package com.jmarkstar.sampletest.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jmarkstar.sampletest.R
import com.jmarkstar.sampletest.extensions.toastLong
import com.jmarkstar.sampletest.repository.FailureReason

open class BaseFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun showTokenExpiredAlert(){

    }

    fun renderMessage(reason: FailureReason) {

        val message = when(reason) {

            //common messages
            FailureReason.SERVER_COULDNT_BE_FOUND -> getString(R.string.server_not_found_error)
            FailureReason.WRONG_VALUES_ON_PARAMETERS -> getString(R.string.wrong_parameters_error)
            FailureReason.DATABASE_OPERATION_ERROR -> getString(R.string.internal_error)
            FailureReason.INTERNAL_ERROR -> getString(R.string.internal_server_error)
            else -> getString(R.string.internal_error)
        }
        toastLong(message)
    }
}