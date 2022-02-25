package ru.iliavolkov.material.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.iliavolkov.material.model.AsteroidDTO
import ru.iliavolkov.material.model.PictureOfTheDayDTO
import ru.iliavolkov.material.utils.END_POINT_ASTEROID
import ru.iliavolkov.material.utils.END_POINT_PICTURE_OF_DAY

interface NasaAPIRequest {

    @GET(END_POINT_PICTURE_OF_DAY)
    fun getPictureOfTheDay(
        @Query("date") date:String,
        @Query("api_key") apiKey: String): Call<PictureOfTheDayDTO>

    @GET(END_POINT_ASTEROID)
    fun getAsteroids(
            @Query("api_key") apiKey: String
    ): Call<AsteroidDTO>
}