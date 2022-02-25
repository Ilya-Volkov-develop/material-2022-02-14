package ru.iliavolkov.material.repository

import retrofit2.Callback
import ru.iliavolkov.material.model.AsteroidDTO

interface RepositoryAsteroids {
    fun getAsteroids(callBack: Callback<AsteroidDTO>)
}