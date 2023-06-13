package com.jh.myownvocabularynotebook.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface TranslationService {

    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    fun translate(
        @Header("X-Naver-Client-Id") clientID: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("text") text: String
    ): Call<TranslationResponseBody>
}