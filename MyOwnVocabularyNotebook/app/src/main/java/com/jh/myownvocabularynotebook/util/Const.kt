package com.jh.myownvocabularynotebook.util

import com.jh.myownvocabularynotebook.R

object Const {

    const val DELAY_SHOW_UI = 50L
    const val DELAY_EXECUTE_TRANSLATION = 1000L
    const val DELAY_DISABLE_CLICK_EVENT = 300L
    const val DELAY_CORRECT_EVENT = 100L
    const val TYPE_GAME = 1
    const val TYPE_EDIT = 2
    const val DB_NAME = "VocabularyNote"
    const val TEXT_XLSX = "xlsx"
    const val CACHE_FILE_NAME = "cache.xlsx"
    const val MIME_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    const val MAX_SIZE_QUESTION = 5
    const val TEXT_KEY = "key"
    const val TEXT_VALUE = "value"
    const val TEXT_RESULT = "result"
    const val TEXT_NOTE_ID = "noteId"
    const val TEXT_SCALE_X = "scaleX"
    const val DELAY_SHOW_MSG = 1000

    val SELECT_LANGUAGE_LIST = listOf(
        SelectLanguageItem(
            id = 0,
            displayCountry = "Korean",
            countryCode = "ko",
            flag = R.drawable.flag_ko
        ),
        SelectLanguageItem(
            id = 1,
            displayCountry = "English",
            countryCode = "en",
            flag = R.drawable.flag_en
        ),
        SelectLanguageItem(
            id = 2,
            displayCountry = "Japanese",
            countryCode = "ja",
            flag = R.drawable.flag_ja
        ),
        SelectLanguageItem(
            id = 3,
            displayCountry = "Simplified Chinese",
            countryCode = "zh-CN",
            flag = R.drawable.flag_zh
        ),
        SelectLanguageItem(
            id = 4,
            displayCountry = "Traditional Chinese",
            countryCode = "zh-TW",
            flag = R.drawable.flag_zh
        ),
        SelectLanguageItem(
            id = 5,
            displayCountry = "Vietnamese",
            countryCode = "vi",
            flag = R.drawable.flag_vi
        ),
        SelectLanguageItem(
            id = 6,
            displayCountry = "Indonesian ",
            countryCode = "id",
            flag = R.drawable.flag_id
        ),
        SelectLanguageItem(
            id = 7,
            displayCountry = "Thai",
            countryCode = "th",
            flag = R.drawable.flag_th
        ),
        SelectLanguageItem(
            id = 8,
            displayCountry = "German",
            countryCode = "de",
            flag = R.drawable.flag_de
        ),
        SelectLanguageItem(
            id = 9,
            displayCountry = "Russian",
            countryCode = "ru",
            flag = R.drawable.flag_ru
        ),
        SelectLanguageItem(
            id = 10,
            displayCountry = "Spanish",
            countryCode = "es",
            flag = R.drawable.flag_es
        ),
        SelectLanguageItem(
            id = 11,
            displayCountry = "Italian",
            countryCode = "it",
            flag = R.drawable.flag_it
        ),
        SelectLanguageItem(
            id = 12,
            displayCountry = "French",
            countryCode = "fr",
            flag = R.drawable.flag_fr
        ),
    )
}