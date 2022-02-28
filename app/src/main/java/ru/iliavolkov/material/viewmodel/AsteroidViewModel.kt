package ru.iliavolkov.material.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.iliavolkov.material.R
import ru.iliavolkov.material.model.AsteroidDTO
import ru.iliavolkov.material.repository.RepositoryImpl
import ru.iliavolkov.material.viewmodel.appstate.AppStateAsteroid

class AsteroidViewModel(private val liveData: MutableLiveData<AppStateAsteroid> = MutableLiveData()): ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy { RepositoryImpl() }

    fun getLiveData() = liveData

    fun getAsteroids(){
        liveData.postValue(AppStateAsteroid.Loading(0))
        repositoryImpl.getAsteroids(callbackAsteroids)
    }

    private val callbackAsteroids = object : Callback<AsteroidDTO> {
        override fun onFailure(call: Call<AsteroidDTO>, t: Throwable) {
            liveData.postValue(AppStateAsteroid.Error(R.string.errorOnServer,0))
        }

        override fun onResponse(call: Call<AsteroidDTO>, response: Response<AsteroidDTO>) {
            if (response.isSuccessful){
                response.body()?.let {
                    liveData.postValue(AppStateAsteroid.Success(it))
                }
            } else liveData.postValue(AppStateAsteroid.Error(R.string.codeError,response.code()))
        }



    }

}