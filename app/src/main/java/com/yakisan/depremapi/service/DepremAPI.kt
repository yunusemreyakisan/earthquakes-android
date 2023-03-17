package com.yakisan.depremapi.service

import com.yakisan.depremapi.model.DepremModel
import io.reactivex.Single
import retrofit2.http.GET

interface DepremAPI {
    //BASE URL -> https://api.berkealp.net/kandilli/

    @GET("index.php?last")
    fun getLastEarthquakes() : Single<ArrayList<DepremModel>>

    @GET("index.php?all")
    fun getAllEarthquakes() : Single<ArrayList<DepremModel>>
}