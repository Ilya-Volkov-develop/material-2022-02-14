package ru.iliavolkov.material

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.iliavolkov.material.repository.NasaAPIRequest
import ru.iliavolkov.material.utils.BASE_URL_CALENDAR
import ru.iliavolkov.material.utils.BASE_URL_NASA

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance: App? = null
        val retrofitMain = Retrofit.Builder().baseUrl(BASE_URL_NASA)
            .client(OkHttpClient())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                ))
            .build().create(NasaAPIRequest::class.java)!!
        val retrofitEarth = Retrofit.Builder().baseUrl(BASE_URL_CALENDAR)
                .client(OkHttpClient())
                .addConverterFactory(
                        GsonConverterFactory.create(
                                GsonBuilder().setLenient().create()
                        ))
                .build().create(NasaAPIRequest::class.java)!!
    }
}