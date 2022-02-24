package ru.iliavolkov.material.repository

import retrofit2.Callback
import ru.iliavolkov.material.App.Companion.retrofit
import ru.iliavolkov.material.BuildConfig
import ru.iliavolkov.material.model.PictureOfTheDayDTO

class RepositoryPictureOfTheDayImpl:RepositoryPictureOfTheDay {

    override fun getPictureOfTheDay(date:String,callBack: Callback<PictureOfTheDayDTO>) {
        retrofit.getPictureOfTheDay(date,BuildConfig.NASA_API_KEY).enqueue(callBack)
    }

}