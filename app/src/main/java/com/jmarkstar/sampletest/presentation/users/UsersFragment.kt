package com.jmarkstar.sampletest.presentation.users


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jmarkstar.sampletest.R
import com.jmarkstar.sampletest.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.jmarkstar.sampletest.databinding.FragmentUsersBinding
import com.jmarkstar.sampletest.extensions.gone
import com.jmarkstar.sampletest.extensions.visible
import com.jmarkstar.sampletest.presentation.Resource
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : BaseFragment() {

    private lateinit var binding: FragmentUsersBinding

    private val usersViewModel: UsersViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =  FragmentUsersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UserAdapter()
        binding.rvUsers.adapter = adapter

        usersViewModel.users.observe(this, Observer {

            when( it ){
                is Resource.Loading -> {
                    tvErrorMessage.gone()
                    pgUserLoader.visible()
                    rvUsers.gone()
                }
                is Resource.Success -> {
                    tvErrorMessage.gone()
                    pgUserLoader.gone()
                    rvUsers.visible()
                    adapter.addItems(it.value)
                }
                is Resource.Empty -> {
                    pgUserLoader.gone()
                    rvUsers.gone()
                    tvErrorMessage.visible()
                    tvErrorMessage.text = getString(R.string.users_empty_message)
                }
                is Resource.Error -> {
                    pgUserLoader.gone()
                    rvUsers.gone()
                    tvErrorMessage.gone()
                    renderMessage(it.reason)
                }
            }
        })
    }

}
