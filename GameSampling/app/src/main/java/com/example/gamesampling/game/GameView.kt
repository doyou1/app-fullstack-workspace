package com.example.gamesampling.game

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.gamesampling.R

class GameView(context: Context, attrs: AttributeSet, defStyle: Int?) :
    View(context, attrs, defStyle ?: 0) {

    private lateinit var paint: Paint
    private lateinit var textPaint: Paint
    private var fontSize: Float = 12f
    private var fontSize2: Float = 20f
    private var borderSize: Float = 2f
    // 要確認
    private var density: Float = resources.displayMetrics.density

    private val bitmaps: ArrayList<Bitmap> = arrayListOf()

    private var status: Int = STATUS_GAME_DESTROYED
    private var frame: Long = 0
    private var score: Long = 0

    private var combatAircraft: CombatAircraft? = null

    init {
        if (defStyle != null) {
            initView(attrs, defStyle)
        } else {
            initView(attrs, 0)
        }
    }

    private fun initView(attrs: AttributeSet?, defStyle: Int) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.GameView, defStyle, 0)
        typedArray.recycle()

        // 初期化paint
        paint = Paint()
        paint.style = Paint.Style.FILL
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.FAKE_BOLD_TEXT_FLAG)
        textPaint.color = 0xff000000.toInt()
        fontSize = textPaint.textSize
        fontSize *= density
        fontSize2 *= density
        textPaint.textSize = fontSize
        borderSize *= density
    }

    fun start(bitmapIds: Array<Int>) {
        destroy()
        for (bitmapId in bitmapIds) {
            val bitmap = BitmapFactory.decodeResource(resources, bitmapId)
            bitmaps.add(bitmap)
        }
        startWhenBitmapsReady()
    }


    fun destroy() {
        destroyNotRecycleBitmaps()

        for (bitmap in bitmaps) {
            bitmap.recycle()
        }
        bitmaps.clear()
    }

    /*----- destroy -----*/
    private fun destroyNotRecycleBitmaps() {
        status = STATUS_GAME_DESTROYED
        frame = 0
        score = 0

        if (combatAircraft != null) {
            combarAircraft.destroy()
        }
    }

    companion object {
        const val STATUS_GAME_STARTED = 1
        const val STATUS_GAME_PAUSED = 2
        const val STATUS_GAME_OVER = 3
        const val STATUS_GAME_DESTROYED = 4


    }
}