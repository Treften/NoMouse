package com.example.nomouse

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import kotlin.math.*

class Mouse(var image: Bitmap, var subdiv:Int)
{
    var x: Int = 0
    var y: Int = 0
    var w: Int = 0
    var h: Int = 0
    var rndSpeed=(1..12).random()
    var rndSize=(1..8).random()
    private var xVelocity = GameSettings.mouseSpeed+rndSpeed
    private var yVelocity = GameSettings.mouseSpeed+rndSpeed
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    var locX:Int=0;
    var locY:Int=0;
    var distanceX:Int=0;
    var distanceY:Int=0;
    private var currxVelocity = xVelocity
    private var curryVelocity = yVelocity
    init
    {
        w = (image.width/12)*GameSettings.mouseScale+15*rndSize
        h = (image.height/12)*GameSettings.mouseScale+15*rndSize

        x = (0..screenWidth).random()
        y = (0..screenHeight).random()
        locX = (0..screenWidth).random()-40
        locY = (0..screenHeight).random()-40
    }

    fun draw(canvas: Canvas)
    {
        val srcRect = Rect(0, 0, image.width, image.height)
        val dstRect = Rect(x, y, x+w, y+h)
        canvas.drawBitmap(image, srcRect, dstRect, null)
    }
    fun movementDirection(x:Int,velocity:Int):Int
    {
        if(x>=0)
        {
        return velocity
        }
        return -velocity
    }
    fun update()
    {
        val distance=sqrt((locX-x).toFloat().pow(2)+(locY-y).toFloat().pow(2));
        if(abs(locX-x)<30)
        {
            currxVelocity=0;
        }
        else
        {
            currxVelocity=xVelocity;
        }
        if(abs(locY-y)<30)
        {
            curryVelocity=0;
        }
        else
        {
            curryVelocity=yVelocity;
        }
        if(distance<80)
        {
            locX = (0..screenWidth-40).random()
            locY = (0..screenHeight-40).random()
        }
        distanceX=locX-x;
        distanceY=locY-y;
        currxVelocity=movementDirection(distanceX, abs(currxVelocity))
        curryVelocity=movementDirection(distanceY,abs(curryVelocity))
        x += (currxVelocity)
        y += (curryVelocity)


    }

}