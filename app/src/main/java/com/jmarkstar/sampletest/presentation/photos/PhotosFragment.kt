package com.jmarkstar.sampletest.presentation.photos


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jmarkstar.sampletest.R
import com.jmarkstar.sampletest.presentation.users.UsersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosFragment : Fragment() {

    private val photosFragment: PhotosViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*usersViewModel.selectedUser.observe(this, Observer { user ->

            Log.v("PhotosFragment","user: ${user.name}")

            photosFragment.photos.observe(this@PhotosFragment, Observer {photos ->


            })
        })*/
    }

}
