package ru.iliavolkov.material.repository

import retrofit2.Callback
import ru.iliavolkov.material.model.PictureOfTheDayDTO

interface RepositoryPictureOfTheDay {
    fun getPictureOfTheDay(date:String,callBack: Callback<PictureOfTheDayDTO>)
}