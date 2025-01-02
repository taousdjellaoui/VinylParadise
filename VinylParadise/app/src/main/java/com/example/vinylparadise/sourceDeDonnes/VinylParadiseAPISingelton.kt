package com.example.vinylparadise.sourceDeDonnes

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VinylParadiseAPISingelton {
    private const val BASE_URL = "http://idefix.dti.crosemont.quebec:9052/"

    val instance: VinylParadiseAPI by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(VinylParadiseAPI::class.java)
    }
}