package ru.iliavolkov.material.repository

import retrofit2.Callback
import ru.iliavolkov.material.App.Companion.retrofit
import ru.iliavolkov.material.BuildConfig
import ru.iliavolkov.material.model.PictureOfTheDayDTO

class RepositoryPictureOfTheDayImpl:RepositoryPictureOfTheDay {

    override fun getPictureOfTheDay(callBack: Callback<PictureOfTheDayDTO>) {
        retrofit.getPictureOfTheDay(BuildConfig.NASA_API_KEY).enqueue(callBack)
    }

}