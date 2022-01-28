package com.wizeline.heroes

import com.wizeline.heroes.model.Characters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeroesServices {

    @GET("characters")
    fun getCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<Characters>

    @GET("characters/{characterId}/comics")
    fun getComicsOfCharacter(
        @Path("characterId") characterId: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<Characters>

    @GET("characters/{characterId}/series")
    fun getSeriesOfCharacter(
        @Path("characterId") characterId: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<Characters>
}
