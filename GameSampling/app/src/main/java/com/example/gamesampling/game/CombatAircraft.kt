package com.example.gamesampling.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class CombatAircraft(bitmap: Bitmap) : Sprite(bitmap) {

    private var collide: Boolean = false
    private var bombAwardCount = 0
    private var single: Boolean = true

    override fun beforeDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        super.beforeDraw(canvas, paint, gameView)
        if(isDestroyed()) {
            validatePosition(canvas)

            if(getFrame() % 7 == 0) {
                fight(gameView)
            }
        }
    }

    private fun validatePosition(canvas: Canvas) {
        if (getX() < 0) {
            setX(0f)
        }
        if(getY() < 0) {
            setY(0f)
        }

        val rectF = getRectF()
        val canvasWidth = canvas.width
        if(rectF.right > canvasWidth) {
            setX((canvasWidth - getWidth()).toFloat())
        }

        val canvasHeight = canvas.height
        if (rectF.bottom > canvasHeight) {
            setY((canvasHeight - getHeight()).toFloat())
        }
    }

    fun getBombCount() : Int {
        return bombAwardCount
    }

    fun fight(gameView: GameView) {
        if (collide || isDestroyed()) return

        val x = getX() + getWidth() / 2
        val y = getY() - 5
        if (single) {
        }
    }
}