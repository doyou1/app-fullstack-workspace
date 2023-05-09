package com.example.vocabularynote.api

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TranslationApiHelper {

    companion object {
        private const val baseUrl = "https://openapi.naver.com/"

        fun getValue(source: String, target: String, key: String): String? {
            val env = dotenv {
                directory = "/assets"
                filename = "env"
            }
            val clientId = env["TRANSLATION_NAVER_API_CLIENT_ID"]
            val clientSecret = env["TRANSLATION_NAVER_API_CLIENT_SECRET"]
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(TranslationService::class.java)
            val call = service.translate(clientId, clientSecret, source, target, key)
            return try {
                call.execute().body()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}