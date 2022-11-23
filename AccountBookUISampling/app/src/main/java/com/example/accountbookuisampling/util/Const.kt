package com.example.accountbookuisampling.util

import android.content.res.Configuration
import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.recyclerview.IncomeInputTextRVAdapter
import com.example.accountbookuisampling.databinding.FragmentRegisterAssetInputBinding
import com.example.accountbookuisampling.dataclass.CalendarItem
import com.example.accountbookuisampling.dataclass.History
import com.example.accountbookuisampling.fragment.registerinput.RegisterIncomeInputFragment

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

const val TEXT_SUNDAY = "일"
const val TEXT_MONDAY = "월"
const val TEXT_TUESDAY = "화"
const val TEXT_WEDNESDAY = "수"
const val TEXT_THURSDAY = "목"
const val TEXT_FRIDAY = "금"
const val TEXT_SATURDAY = "토"

const val COLOR_SUNDAY = Color.RED
const val COLOR_WEEKDAY = Color.GRAY
const val COLOR_SATURDAY = Color.BLUE

const val CALENDAR_HEAD_HEIGHT_RATIO = 20
const val CALENDAR_CONTENT_HEIGHT_RATIO = 5

const val TEXT_ADD = "추가"
const val TEXT_FIRST_DAY_OF_MONTH = "01"
const val TEXT_DATE = "날짜"
const val TEXT_ASSET = "자산"
const val TEXT_CATEGORY = "분류"
const val TEXT_AMOUNT = "금액"

val CALENDAR_HEAD_LIST = arrayOf(
    CalendarItem(TYPE_CALENDAR_HEAD, 0,"", 0, 0, 0),
    CalendarItem(TYPE_CALENDAR_HEAD, 1,"", 0, 0, 0),
    CalendarItem(TYPE_CALENDAR_HEAD, 2,"", 0, 0, 0),
    CalendarItem(TYPE_CALENDAR_HEAD, 3,"", 0, 0, 0),
    CalendarItem(TYPE_CALENDAR_HEAD, 4,"", 0, 0, 0),
    CalendarItem(TYPE_CALENDAR_HEAD, 5,"", 0, 0, 0),
    CalendarItem(TYPE_CALENDAR_HEAD, 6,"", 0, 0, 0)
)

const val FLAG_NOT_CALENDAR_HEAD = -1
const val CALENDAR_VIEW_SPAN_COUNT = 7
const val INPUT_ITEM_VIEW_SPAN_COUNT = 4

const val TAG_DATE = "DATE"
const val TAG_ASSET = "ASSET"
const val TAG_CATEGORY = "CATEGORY"
const val TAG_AMOUNT = "AMOUNT"

const val TEXT_DIVIDE = "÷"
const val TEXT_MULTIPLICATION = "×"
const val TEXT_SUBTRACTION = "－"
const val TEXT_ADDITION = "＋"
const val TEXT_SEVEN = "7"
const val TEXT_EIGHT = "8"
const val TEXT_NINE = "9"
const val TEXT_EQUAL = "＝"
const val TEXT_FOUR = "4"
const val TEXT_FIVE = "5"
const val TEXT_SIX = "6"
const val TEXT_DOT = "."
const val TEXT_ONE = "1"
const val TEXT_TWO = "2"
const val TEXT_THREE = "3"
const val TEXT_REMOVE = "←"
const val TEXT_EMPTY = ""
const val TEXT_ZERO = "0"
const val TEXT_CONFIRM = "확인"

val CALCULATOR_ITEM_LIST = arrayOf(
    TEXT_DIVIDE,
    TEXT_MULTIPLICATION,
    TEXT_SUBTRACTION,
    TEXT_ADDITION,
    TEXT_SEVEN,
    TEXT_EIGHT,
    TEXT_NINE,
    TEXT_EQUAL,
    TEXT_FOUR,
    TEXT_FIVE,
    TEXT_SIX,
    TEXT_DOT,
    TEXT_ONE,
    TEXT_TWO,
    TEXT_THREE,
    TEXT_REMOVE,
    TEXT_EMPTY,
    TEXT_ZERO,
    TEXT_EMPTY,
    TEXT_CONFIRM
)

const val TEXT_ENN = "￥"