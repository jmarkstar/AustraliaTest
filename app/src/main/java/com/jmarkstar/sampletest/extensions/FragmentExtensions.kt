package com.jmarkstar.sampletest.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions

fun Fragment.singleTopDestination(): NavOptions {
    return NavOptions.Builder().setLaunchSingleTop(true).build()
}

fun Fragment.toast(message: String){
    Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastLong(message: String){
    Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
}