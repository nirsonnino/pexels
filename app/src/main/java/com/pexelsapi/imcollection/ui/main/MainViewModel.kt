package com.pexelsapi.imcollection.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.pexelsapi.imcollection.common.BaseViewModel
import com.pexelsapi.imcollection.common.EventState
import com.pexelsapi.imcollection.model.PexelsApiResponse
import com.pexelsapi.imcollection.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {
    private val photos = MutableLiveData<List<PexelsApiResponse.Photos>?>()
    private val isLoading = MutableLiveData(false)
    private val people = MutableLiveData<List<PexelsApiResponse.Photos>?>()

    private val event = MutableLiveData<MainState.Event?>()
    private val eventState = MediatorLiveData<EventState<MainState.Event>?>()

    init {
        getPopularPeople()
        getNewPhotos()

        eventState.addSource(event) {
            eventState.postValue(it?.run { EventState(this) })
        }
    }

    fun getNewPhotos() = getPhotos("new")

    private fun getPhotos(query: String? = null) {
        addDisposable {
            apiService.searchPhotos(query, 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoading.postValue(true)
                }
                .doFinally {
                    isLoading.postValue(false)
                }
                .subscribe({
                    photos.postValue(it.photos)
                }) {
                    event.postValue(MainState.Event.Error(it.message))
                }
        }
    }

    private fun getPopularPeople() {
        addDisposable {
            apiService.searchPhotos("popular", 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoading.postValue(true)
                }
                .doFinally {
                    isLoading.postValue(false)
                }
                .subscribe({
                    people.postValue(it.photos)
                }) {
                    event.postValue(MainState.Event.Error(it.message))
                }
        }
    }

    fun getPhotos() : LiveData<List<PexelsApiResponse.Photos>?> = photos
    fun getIsLoading() : LiveData<Boolean> = isLoading
    fun getPeople() : LiveData<List<PexelsApiResponse.Photos>?> = people
    fun getEvent(): LiveData<EventState<MainState.Event>?> = eventState

    fun onClickImage(data: PexelsApiResponse.Photos?) {
        data?.let {
            event.postValue(MainState.Event.ShowPhotoDetail(it))
        }
    }
}