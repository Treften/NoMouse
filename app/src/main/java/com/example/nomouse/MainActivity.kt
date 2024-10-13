package com.example.nomouse

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


object GameSettings
{
    var mouseNum=3
    var mouseScale=80
    var mouseSpeed=20
    var mouseTouchNum=0
    var touchNum=0
    var gameTime=0.0f
}
class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
{
    companion object
    {
        const val DB_NAME = "gameStats.db"
        const val DB_VERSION = 1
    }
    override fun onCreate(db: SQLiteDatabase)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS gameStats(" +
                "id INTEGER PRIMARY KEY, " +
                "touchNum INT, " +
                "mouseTouchNum INT, " +
                "gameTime INT)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {

    }
}
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onDestroy()
    {
        super.onDestroy()
        val helper = DbHelper(this)
        val db = helper.writableDatabase
        val cv = ContentValues()
        cv.put("touchNum", GameSettings.touchNum)
        cv.put("mouseTouchNum", GameSettings.mouseTouchNum)
        cv.put("gameTime", GameSettings.gameTime)
        val rowID = db.insert("gameStats", null, cv)
    }
}