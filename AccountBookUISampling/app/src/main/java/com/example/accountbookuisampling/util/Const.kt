package com.example.accountbookuisampling.util

import com.example.accountbookuisampling.dataclass.History

const val MAIN_FRAGMENT_COUNT = 5
const val REGISTER_FRAGMENT_COUNT = 3


const val DAY = "DAY"
const val CALENDAR = "CALENDAR"
const val WEEK = "WEEK"
const val MONTH = "MONTH"
const val YEAR = "YEAR"
const val TYPE_CALENDAR_HEAD = 0
const val TYPE_CALENDAR_CONTENT = 1

const val INCOME = "INCOME"
const val CONSUMPTION = "CONSUMPTION"
const val TRANSFER = "TRANSFER"

val tempHistoryList = arrayListOf(
    History("202009201511", "숙소비", "M카드", "1700000"),
    History("202009201511", "숙소비", "M카드", "1700000"),
    History("202009201511", "숙소비", "M카드", "1700000"),
    History("202009201511", "숙소비", "M카드", "1700000"),
    History("202009201511", "숙소비", "M카드", "1700000"),
    History("202009201511", "숙소비", "M카드", "1700000"),
    History("202009201511", "숙소비", "M카드", "1700000"),
    History("202009201511", "숙소비", "M카드", "1700000")
)

const val FLAG_DATE = 1
const val FLAG_ASSET = 2
const val FLAG_CATEGORY = 3
const val FLAG_AMOUNT = 4