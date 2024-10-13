package com.example.nomouse

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Rect
import android.media.Image
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.SurfaceHolder



class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {
    private val thread: GameThread
    private var mouse: Mouse? = null
    var mouses= mutableListOf<Mouse>()
    var touchNum = 0
    var mouseTouchNum = 0
    private lateinit var backgroundBitmap: Bitmap
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    init {
        holder.addCallback(this)

        thread = GameThread(holder, this)
        backgroundBitmap = BitmapFactory.decodeResource(resources, R.drawable.floor)

    }


    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        for (i in 0 until GameSettings.mouseNum)
        {
            mouses.add(Mouse(BitmapFactory.decodeResource(resources, R.drawable.mousep),i+2))
        }


        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            retry = false
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
        touchNum++
        GameSettings.touchNum=touchNum
        val touched_x = event.x.toInt()
        val touched_y = event.y.toInt()
        for(m in mouses)
        {

                if (touched_x >= m.x && touched_x < (m.x + m.w)
                    && touched_y >= m.y && touched_y < (m.y + m.h))
                {
                    mouseTouchNum++
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                    GameSettings.mouseTouchNum=mouseTouchNum
                    return true
                }
            }
        }
        return true
    }
    fun update() {
        for(m in mouses)
        {
            m!!.update()
        }
       // mouse!!.update()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
       // val srcRect = Rect(0, 0, backgroundBitmap.width, backgroundBitmap.height)
       // val dstRect = Rect(0, 0, screenWidth, screenHeight)
        val matrix = Matrix()
        matrix.postRotate(90f, backgroundBitmap.width / 2f, backgroundBitmap.height / 2f)
        matrix.postTranslate((screenWidth - backgroundBitmap.width) / 2f, (screenHeight - backgroundBitmap.height) / 2f)
        canvas.drawBitmap(backgroundBitmap, matrix, null)
        for(m in mouses)
        {
            m!!.draw(canvas)
        }
       // mouse!!.draw(canvas)
    }
}