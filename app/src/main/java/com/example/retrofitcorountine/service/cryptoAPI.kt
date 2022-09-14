package com.example.retrofitcorountine.service

import com.example.retrofitcorountine.model.cryptoModel
import retrofit2.Response
import retrofit2.http.GET


interface cryptoAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData(): Response<List<cryptoModel>>


}