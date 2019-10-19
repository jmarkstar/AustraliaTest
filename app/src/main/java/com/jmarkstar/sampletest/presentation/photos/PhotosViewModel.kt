package com.jmarkstar.sampletest.presentation.photos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmarkstar.sampletest.extensions.setLoading
import com.jmarkstar.sampletest.models.Photo
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.presentation.Resource
import com.jmarkstar.sampletest.repository.PhotoRepository
import com.jmarkstar.sampletest.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosViewModel constructor(private val photoRepository: PhotoRepository): ViewModel() {

    var photos = MutableLiveData<Resource<List<Photo>>>()

    var selectedPhoto = MutableLiveData<Photo>()

    init {
        photos.value = Resource.Empty
    }

    fun getPhotos(selectedUser: User, refresh: Boolean = true) = viewModelScope.launch {
        photos.setLoading()

        val usersResult = withContext(Dispatchers.Default) {

            when( val result = photoRepository.getUserPhotos(selectedUser, refresh)) {

                is Result.Success -> {
                    Log.v("PhotosViewModel","getPhotos size ${result.value.size}")
                    if(result.value.isNotEmpty()) {
                        Resource.Success(result.value)
                    } else {
                        Resource.Empty
                    }
                }
                is Result.Failure -> {
                    Resource.Error(result.reason)
                }
            }
        }

        photos.value = usersResult
    }

    fun select(photo: Photo){
        selectedPhoto.value = photo
    }
}