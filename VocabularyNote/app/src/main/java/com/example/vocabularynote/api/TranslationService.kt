package com.example.vocabularynote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TranslationService {

    @GET("v1/papago/n2mt")
    fun translate(
        @Header("X-Naver-Client-Id") clientID: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("source") source: String,
        @Query("target") target: String,
        @Query("text") text: String
    ): Call<String>
}