package viewmodel.appstate

import ru.iliavolkov.material.model.PictureOfTheDayDTO

sealed class AppStatePictureOfTheDay {
    data class Loading(val progress:Int): AppStatePictureOfTheDay()
    data class Success(var pictureData:PictureOfTheDayDTO): AppStatePictureOfTheDay()
    data class Error(val error:Int, val code:Int): AppStatePictureOfTheDay()

}