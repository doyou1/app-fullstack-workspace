package com.example.accountbookuisampling.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.PrimaryKey
import androidx.room.Room
import com.example.accountbookuisampling.room.AppDataBase
import com.example.accountbookuisampling.room.entity.Asset
import com.example.accountbookuisampling.room.entity.Category
import com.example.accountbookuisampling.room.entity.History
import com.example.accountbookuisampling.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

class BaseApplication : Application() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME).build()
    }

    val assetDao by lazy {
        db.assetDao()
    }

    val categoryDao by lazy {
        db.categoryDao()
    }

    val historyDao by lazy {
        db.historyDao()
    }

    private val TAG = this::class.java.simpleName

    override fun onCreate() {
        super.onCreate()

        val preferences = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE)
        val isInit = preferences.getBoolean(TEXT_INIT, false)

        if (!isInit) {
            CoroutineScope(Dispatchers.IO).launch {
                assetDao.insertAll(getInitAssetList())
                categoryDao.insertAll(getInitCategoryList())
                val assetSize = assetDao.getSize()
                val categorySize = categoryDao.getSize()
                historyDao.insertAll(getInitHistoryList(assetSize, categorySize))
                CoroutineScope(Dispatchers.Main).launch {
                    preferences.edit().putBoolean(TEXT_INIT, true).apply()
                }
            }
        }
    }

    private fun getInitAssetList(): List<Asset> {
        val list = ArrayList<Asset>()

        var count = 0
        for (name in INIT_INCOME_ASSETS) {
            val item = Asset(
                0,
                0,
                name,
                Random.nextInt(0, 100000),
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_CONSUMPTION_ASSETS) {
            val item = Asset(
                0,
                1,
                name,
                -1,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_TRANSFER_ASSETS) {
            val item = Asset(
                0,
                2,
                name,
                -1,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        return list.toList()
    }

    private fun getInitCategoryList(): List<Category> {
        val list = ArrayList<Category>()

        var count = 0
        for (name in INIT_INCOME_CATEGORIES) {
            val item = Category(
                0,
                0,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_CONSUMPTION_CATEGORIES) {
            val item = Category(
                0,
                1,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_TRANSFER_CATEGORIES) {
            val item = Category(
                0,
                2,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        return list.toList()
    }

    private fun getInitHistoryList(assetSize: Int, categorySize: Int): List<History> {
        val list = ArrayList<History>()
        for (i in 0..10000) {
            val id = i + 1
            val date = getRandomDate()
            // 0:income, 1:consumption, 2:transfer
            val type = i % 3

            val assetId = randBetween(1, assetSize)
            val assetName = assetDao.getNameByid(assetId)

            val categoryId = randBetween(1, categorySize)
            val categoryName = categoryDao.getNameByid(categoryId)

            val amount = Random.nextInt(100, 10000)
            val detail = if (i % 3 == 0) null else "detail${String.format("%02d", i)}"

            val item = History(
                id,
                type,
                date,
                assetId,
                assetName,
                categoryId,
                categoryName,
                amount,
                detail,
                null,
                null
            )

            list.add(item)
        }
        return list.toList()
    }

    private fun getRandomDate(): String {
        val cal = Calendar.getInstance()

        val year = 2022
        cal.set(Calendar.YEAR, year)
        val dayOfYear = randBetween(1, cal.getActualMaximum(Calendar.DAY_OF_YEAR))
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear)

        val yyyy = cal.get(Calendar.YEAR)
        val MM = String.format(
            "%02d",
            cal.get(Calendar.MONTH) + 1
        )
        val dd = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))

        val hh = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))
        val mm = String.format("%02d", cal.get(Calendar.MINUTE))

        return "$yyyy$MM$dd$hh$mm"
    }

    private fun randBetween(start: Int, end: Int): Int {
        return start + (Math.random() * (end - start)).roundToInt()
    }

}