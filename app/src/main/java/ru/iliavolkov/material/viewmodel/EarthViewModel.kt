package ru.iliavolkov.material.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.iliavolkov.material.R
import ru.iliavolkov.material.model.DateDTO
import ru.iliavolkov.material.model.Earth
import ru.iliavolkov.material.repository.RepositoryImpl
import ru.iliavolkov.material.viewmodel.appstate.AppStateAllDate
import ru.iliavolkov.material.viewmodel.appstate.AppStateEarthImages

class EarthViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData()): ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy { RepositoryImpl() }

    fun getLiveData() = liveData

    fun getAllDate(){
        liveData.postValue(AppStateAllDate.Loading(0))
        repositoryImpl.getAllDate(callbackGetAllDate)
    }

    fun getImageOnDate(day:String,month:String,year:String){
        liveData.postValue(AppStateEarthImages.Loading(0))
        repositoryImpl.getImageOnDate(day,month,year,callbackGetEarthImages)
    }


    private val callbackGetAllDate = object : Callback<List<DateDTO>> {
        override fun onFailure(call: Call<List<DateDTO>>, t: Throwable) {
            liveData.postValue(AppStateAllDate.Error(R.string.errorOnServer,0))
        }

        override fun onResponse(call: Call<List<DateDTO>>, response: Response<List<DateDTO>>) {
            if (response.isSuccessful){
                response.body()?.let {
                    liveData.postValue(AppStateAllDate.Success(it))
                }
            } else liveData.postValue(AppStateAllDate.Error(R.string.codeError,response.code()))
        }



    }

    private val callbackGetEarthImages = object : Callback<List<Earth>> {
        override fun onFailure(call: Call<List<Earth>>, t: Throwable) {
            liveData.postValue(AppStateEarthImages.Error(R.string.errorOnServer,0))
        }

        override fun onResponse(call: Call<List<Earth>>, response: Response<List<Earth>>) {
            if (response.isSuccessful){
                response.body()?.let {
                    liveData.postValue(AppStateEarthImages.Success(it))
                }
            } else liveData.postValue(AppStateEarthImages.Error(R.string.codeError,response.code()))
        }



    }

}