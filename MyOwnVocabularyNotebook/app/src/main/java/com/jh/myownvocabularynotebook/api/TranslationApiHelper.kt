package com.jh.myownvocabularynotebook.api

import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TranslationApiHelper {

    companion object {
        private const val baseUrl = "https://openapi.naver.com/"
        private val env = dotenv {
            directory = "/assets"
            filename = "env"
        }
        private val clientId = env["TRANSLATION_NAVER_API_CLIENT_ID"]
        private val clientSecret = env["TRANSLATION_NAVER_API_CLIENT_SECRET"]

        suspend fun getValue(
            source: String,
            target: String,
            key: String
        ): TranslationResponseBody? = withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(TranslationService::class.java)
            val call = service.translate(clientId, clientSecret, source, target, key)
            return@withContext try {
                call.execute().body()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}