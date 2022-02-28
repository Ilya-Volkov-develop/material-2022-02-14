package ru.iliavolkov.material.viewmodel.appstate

import ru.iliavolkov.material.model.DateDTO

sealed class AppStateAllDate {
    data class Loading(val progress:Int): AppStateAllDate()
    data class Success(var listAllDate: List<DateDTO>): AppStateAllDate()
    data class Error(val error:Int, val code:Int): AppStateAllDate()

}