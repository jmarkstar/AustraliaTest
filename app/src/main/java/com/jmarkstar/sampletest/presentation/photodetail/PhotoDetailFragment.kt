package com.jmarkstar.sampletest.presentation.photodetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmarkstar.sampletest.databinding.FragmentPhotoDetailBinding
import com.jmarkstar.sampletest.presentation.base.BaseFragment
import com.jmarkstar.sampletest.presentation.photos.PhotosViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PhotoDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentPhotoDetailBinding

    private val photosViewModel: PhotosViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =  FragmentPhotoDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = photosViewModel
        return binding.root
    }
}
