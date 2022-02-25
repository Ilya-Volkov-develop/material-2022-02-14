package ru.iliavolkov.material.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.iliavolkov.material.R
import ru.iliavolkov.material.model.PictureOfTheDayDTO
import ru.iliavolkov.material.repository.RepositoryImpl
import ru.iliavolkov.material.viewmodel.appstate.AppStatePictureOfTheDay

class PictureOfTheDayViewModel(private val liveData: MutableLiveData<AppStatePictureOfTheDay> = MutableLiveData()): ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy {
        RepositoryImpl()
    }

    fun getLiveData() = liveData

    fun getPictureOfTheDay(date:String){
        liveData.postValue(AppStatePictureOfTheDay.Loading(0))
        repositoryImpl.getPictureOfTheDay(date,callbackPictureOfTheDay)
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