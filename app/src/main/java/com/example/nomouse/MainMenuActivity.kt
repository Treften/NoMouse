package com.example.nomouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.math.MathUtils.clamp
import kotlin.math.*

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NoMouse)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val startButton: Button = findViewById(R.id.buttonStart)
        val statsButton: Button = findViewById(R.id.buttonResults)

        val seekNum = findViewById<SeekBar>(R.id.seekbarNum)
        val numTextView: TextView = findViewById(R.id.textViewNum)
        val seekSpeed = findViewById<SeekBar>(R.id.seekbarSpeed)
        val speedTextView: TextView = findViewById(R.id.textViewSpeed)
        val seekSize = findViewById<SeekBar>(R.id.seekbarSize)
        val sizeTextView: TextView = findViewById(R.id.textViewSize)
        seekNum?.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    numTextView.text="Количество мышей: "+progress.toString()
                }

                override fun onStartTrackingTouch(seek: SeekBar) {

                }

                override fun onStopTrackingTouch(seek: SeekBar) {

                }
            })
        seekSpeed?.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    speedTextView.text="Минимальная скорость: "+progress.toString()
                }

                override fun onStartTrackingTouch(seek: SeekBar) {

                }

                override fun onStopTrackingTouch(seek: SeekBar) {

                }
            })
        seekSize.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    sizeTextView.text="Минимальный размер: "+progress.toString()
                }

                override fun onStartTrackingTouch(seek: SeekBar) {

                }

                override fun onStopTrackingTouch(seek: SeekBar) {

                }
            })

        startButton.setOnClickListener {
            GameSettings.mouseNum=seekNum.progress
            GameSettings.mouseSpeed=seekSpeed.progress
            GameSettings.mouseScale=seekSize.progress
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        statsButton.setOnClickListener{
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }
    }
}