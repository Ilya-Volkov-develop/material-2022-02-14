package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.iliavolkov.material.R
import ru.iliavolkov.material.model.PictureOfTheDayDTO
import ru.iliavolkov.material.repository.RepositoryPictureOfTheDayImpl
import viewmodel.appstate.AppStatePictureOfTheDay

class PictureOfTheDayViewModel(private val liveData: MutableLiveData<AppStatePictureOfTheDay> = MutableLiveData()): ViewModel() {

    private val repositoryPictureOfTheDayImpl: RepositoryPictureOfTheDayImpl by lazy {
        RepositoryPictureOfTheDayImpl()
    }

    fun getLiveData() = liveData

    fun getPictureOfTheDay(){
        liveData.postValue(AppStatePictureOfTheDay.Loading(0))
        repositoryPictureOfTheDayImpl.getPictureOfTheDay(callbackPictureOfTheDay)
    }

    private val callbackPictureOfTheDay = object : Callback<PictureOfTheDayDTO> {
        override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
            liveData.postValue(AppStatePictureOfTheDay.Error(R.string.errorOnServer,0))
        }

        override fun onResponse(call: Call<PictureOfTheDayDTO>, response: Response<PictureOfTheDayDTO>) {
            if (response.isSuccessful){
                response.body()?.let {
                    liveData.postValue(AppStatePictureOfTheDay.Success(it))
                }
            } else liveData.postValue(AppStatePictureOfTheDay.Error(R.string.codeError,response.code()))
        }



    }

}