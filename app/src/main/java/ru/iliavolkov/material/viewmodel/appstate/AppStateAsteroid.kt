package ru.iliavolkov.material.viewmodel.appstate

import ru.iliavolkov.material.model.AsteroidDTO

sealed class AppStateAsteroid {
    data class Loading(val progress:Int): AppStateAsteroid()
    data class Success(var asteroidData:AsteroidDTO): AppStateAsteroid()
    data class Error(val error:Int, val code:Int): AppStateAsteroid()

}