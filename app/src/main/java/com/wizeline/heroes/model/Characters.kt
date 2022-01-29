package com.wizeline.heroes.model

import com.google.gson.annotations.SerializedName

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
    val resourceURI: String? = null
)

data class Thumbnail(
    val path: String? = null,
    val extension: String? = null
)
