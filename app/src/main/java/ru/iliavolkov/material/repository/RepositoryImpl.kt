package ru.iliavolkov.material.repository

import retrofit2.Callback
import ru.iliavolkov.material.App.Companion.retrofitEarth
import ru.iliavolkov.material.App.Companion.retrofitMain
import ru.iliavolkov.material.BuildConfig
import ru.iliavolkov.material.model.AsteroidDTO
import ru.iliavolkov.material.model.DateDTO
import ru.iliavolkov.material.model.Earth
import ru.iliavolkov.material.model.PictureOfTheDayDTO

class RepositoryImpl:RepositoryPictureOfTheDay,RepositoryAsteroids,RepositoryEarth {

    override fun getPictureOfTheDay(date:String,callBack: Callback<PictureOfTheDayDTO>) {
        retrofitMain.getPictureOfTheDay(date,BuildConfig.NASA_API_KEY).enqueue(callBack)
    }

    override fun getAsteroids(callBack: Callback<AsteroidDTO>) {
        retrofitMain.getAsteroids(BuildConfig.NASA_API_KEY).enqueue(callBack)
    }

    override fun getAllDate(callBack: Callback<List<DateDTO>>) {
        retrofitEarth.getAllDate().enqueue(callBack)
    }

    override fun getImageOnDate(day: String, month: String, year: String, callback: Callback<List<Earth>>) {
        retrofitEarth.getEarthImages("$year-$month-$day").enqueue(callback)
    }

}