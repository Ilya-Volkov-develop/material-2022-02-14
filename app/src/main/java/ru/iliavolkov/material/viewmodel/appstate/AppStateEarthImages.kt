package ru.iliavolkov.material.viewmodel.appstate

import ru.iliavolkov.material.model.Earth

sealed class AppStateEarthImages {
    data class Loading(val progress:Int): AppStateEarthImages()
    data class Success(var listEarthImages: List<Earth>): AppStateEarthImages()
    data class Error(val error:Int, val code:Int): AppStateEarthImages()

}