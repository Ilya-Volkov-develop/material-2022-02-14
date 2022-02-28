package ru.iliavolkov.material.repository

import retrofit2.Callback
import ru.iliavolkov.material.model.DateDTO
import ru.iliavolkov.material.model.Earth

interface RepositoryEarth {
    fun getAllDate(callBack: Callback<List<DateDTO>>)
    fun getImageOnDate(day:String,month:String,year:String,callback: Callback<List<Earth>>)
}