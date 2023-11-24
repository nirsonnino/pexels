package com.pexelsapi.imcollection.network

import android.database.Observable
import com.pexelsapi.imcollection.model.PexelsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(NetworkConstants.ENDPOINT_SEARCH)
    fun searchPhotos(@Query("query") category: String?, @Query("per_page") perPage: Int) : io.reactivex.Observable<PexelsApiResponse>
}