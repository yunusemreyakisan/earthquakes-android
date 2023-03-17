package com.yakisan.depremapi.service

import com.yakisan.depremapi.model.DepremModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class APIClient {
    //BASE URL -> https://api.berkealp.net/kandilli/
    private val BASE_URL = "https://api.berkealp.net/kandilli/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava
        .build()
        .create(DepremAPI::class.java)

    fun getAllEartquakes() : Single<ArrayList<DepremModel>> {
        return api.getAllEarthquakes()
    }

    fun getLastEartquake() : Single<ArrayList<DepremModel>>{
        return api.getLastEarthquakes()
    }
}