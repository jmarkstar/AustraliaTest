package com.jmarkstar.sampletest.presentation.users


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmarkstar.sampletest.R
import com.jmarkstar.sampletest.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BaseFragment() {

    private val usersViewModel: UsersViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}
