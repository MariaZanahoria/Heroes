package com.wizeline.heroes.model

import com.google.gson.annotations.SerializedName
import com.wizeline.heroes.BuildConfig.API_KEY
import com.wizeline.heroes.HASH
import com.wizeline.heroes.TS

data class Characters(
    val code: Int? = null,
    val status: String? = null,
    val copyright: String? = null,
    val attributionText: String? = null,
    val attributionHTML: String? = null,
    val etag: String? = null,
    val data: Data? = null
)

data class Data(
    val offset: Int? = null,
    val limit: Int? = null,
    val total: Int? = null,
    val count: Int? = null,
    @SerializedName("results")
    val characterInfo: List<CharacterInfo>? = null,
)

data class CharacterInfo(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val modified: String? = null,
    val thumbnail: Thumbnail? = null,
    val resourceURI: String? = null,
    val comics: ComicList? = null
)

data class ComicList(
    val available: Int? = null,
    val returned: Int? = null,
)

data class Thumbnail(
    val path: String? = null,
    val extension: String? = null
) {
    fun getUrl(): String {
        return path?.replace(
            "http",
            "https"
        ) + "/portrait_small.jpg?ts=$TS&apikey=$API_KEY&hash=$HASH"
    }
}

data class CharacterDataWrapper(
    val code: Int? = null,
    val status: String? = null,
    val data: CharacterDataContainer? = null
)

data class CharacterDataContainer(
    val results: List<CharacterInfo>? = null
)
