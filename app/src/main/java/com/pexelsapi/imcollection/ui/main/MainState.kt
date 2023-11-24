package com.pexelsapi.imcollection.ui.main

import com.pexelsapi.imcollection.model.PexelsApiResponse

class MainState {
    sealed class Event {
        data class ShowPhotoDetail(val data: PexelsApiResponse.Photos) : Event()
        data class Error(val error: String?) : Event()
    }
}