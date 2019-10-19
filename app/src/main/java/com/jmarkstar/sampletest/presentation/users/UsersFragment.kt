package com.jmarkstar.sampletest.presentation.users


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jmarkstar.sampletest.R
import com.jmarkstar.sampletest.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.jmarkstar.sampletest.databinding.FragmentUsersBinding

class UsersFragment : BaseFragment() {

    private lateinit var binding: FragmentUsersBinding

    private val usersViewModel: UsersViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =  FragmentUsersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val adapter = UserAdapter()
        adapter.onItemClick = {user ->

            usersViewModel.select(user)
            findNavController().navigate(R.id.action_usersFragment_to_photosFragment)
        }

        binding.rvUsers.adapter = adapter

        binding.viewModel = usersViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersViewModel.error.observe(this, Observer {
            renderMessage(it)
        })
    }
}
