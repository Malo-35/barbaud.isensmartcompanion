package fr.isen.barbaud.isensmartcompanion.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val baseurl = "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}