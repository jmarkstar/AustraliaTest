package com.jmarkstar.sampletest.presentation.photos


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jmarkstar.sampletest.R
import com.jmarkstar.sampletest.databinding.FragmentPhotosBinding
import com.jmarkstar.sampletest.presentation.base.BaseFragment
import com.jmarkstar.sampletest.presentation.users.UsersViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PhotosFragment : BaseFragment() {

    private lateinit var binding: FragmentPhotosBinding

    private val usersViewModel: UsersViewModel by sharedViewModel()

    private val photosViewModel: PhotosViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =  FragmentPhotosBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val adapter = PhotoAdapter()
        adapter.onItemClick = {photo ->

            photosViewModel.select(photo)
            findNavController().navigate(R.id.action_photosFragment_to_photoDetailFragment)
        }

        binding.rvPhotos.adapter = adapter

        binding.viewModel = photosViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photosViewModel.error.observe(this, Observer {
            renderMessage(it)
        })

        usersViewModel.selectedUser.observe(this, Observer { user ->
            Log.v("PhotosFragment","user: ${user.name}")
            photosViewModel.getPhotos(user)
        })
    }
}
