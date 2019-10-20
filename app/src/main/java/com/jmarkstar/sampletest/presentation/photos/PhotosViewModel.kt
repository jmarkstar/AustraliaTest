package com.jmarkstar.sampletest.presentation.photos

import androidx.lifecycle.*
import com.jmarkstar.sampletest.models.Photo
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.presentation.common.BaseViewModel
import com.jmarkstar.sampletest.presentation.common.CoroutineContextProvider
import com.jmarkstar.sampletest.repository.FailureReason
import com.jmarkstar.sampletest.repository.PhotoRepository
import com.jmarkstar.sampletest.repository.Result
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosViewModel constructor(private val photoRepository: PhotoRepository,
                                  coroutineContextProvider: CoroutineContextProvider): BaseViewModel(coroutineContextProvider) {

    private var _photos = MutableLiveData<List<Photo>>()
    private var _isLoading = MutableLiveData<Boolean>()
    private var _error = MutableLiveData<FailureReason>()
    private var _selectedPhoto = MutableLiveData<Photo>()

    val photos: LiveData<List<Photo>> = _photos
    val isLoading: LiveData<Boolean> = _isLoading
    val isPhotosEmpty: LiveData<Boolean> = Transformations.map(_photos) { it.isEmpty() }
    val error: LiveData<FailureReason> = _error

    val selectedPhoto: LiveData<Photo> = _selectedPhoto

    fun getPhotos(selectedUser: User, refresh: Boolean = false) = launch {

        _isLoading.value = true

        withContext(coroutineContextProvider.Default) {

            when( val result = photoRepository.getUserPhotos(selectedUser, refresh)) {

                is Result.Success -> {
                    setPhotos(result.value)
                }
                is Result.Failure -> {
                    setError(result.reason)
                }
            }
        }

        _isLoading.value = false
    }

    private suspend fun setPhotos(items: List<Photo>) = withContext(coroutineContextProvider.Main){
        _photos.value = items
    }

    private suspend fun setError(reason: FailureReason) = withContext(coroutineContextProvider.Main){
        _error.value = reason
    }

    fun select(photo: Photo){
        _selectedPhoto.value = photo
    }
}