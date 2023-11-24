package com.pexelsapi.imcollection.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PexelsApiResponse (
    @Expose @SerializedName("page")
    var page: Int? = null,
    @Expose @SerializedName("per_page")
    var perPage: Int? = null,
    @Expose @SerializedName("photos")
    var photos: List<Photos>? = null,
    @Expose @SerializedName("total_results")
    var totalResults: Int? = null,
    @Expose @SerializedName("next_page")
    var nextPage: String? = null,
) : Parcelable {
    @Parcelize
    data class Photos (
        @Expose @SerializedName("id")
        var id: Int? = null,
        @Expose @SerializedName("width")
        var width: Int? = null,
        @Expose @SerializedName("height")
        var height: Int? = null,
        @Expose @SerializedName("url")
        var url: String? = null,
        @Expose @SerializedName("photographer")
        var photographer: String? = null,
        @Expose @SerializedName("photographer_url")
        var photographerUrl: String? = null,
        @Expose @SerializedName("photographer_id")
        var photographerId: Int? = null,
        @Expose @SerializedName("avg_color")
        var avgColor: String? = null,
        @Expose @SerializedName("src")
        var src: Src? = null,
        @Expose @SerializedName("liked")
        var liked: Boolean? = null,
        @Expose @SerializedName("alt")
        var alt: String? = null
    ) : Parcelable

    @Parcelize
    data class Src (
        @Expose @SerializedName("original")
        var original: String? = null,
        @Expose @SerializedName("portrait")
        var portrait: String? = null
    ) : Parcelable
}