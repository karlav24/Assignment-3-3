package com.example.assignment33

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var interestingTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val celsiusSeekBar: SeekBar = findViewById(R.id.cSeekBar)
        val fahrenheitSeekBar: SeekBar = findViewById(R.id.fSeekBar)
        val celsiusTextView: TextView = findViewById(R.id.cTextView)
        val fahrenheitTextView: TextView = findViewById(R.id.fTextView)
        interestingTextView = findViewById(R.id.interestingTextView)
        fahrenheitSeekBar.progress = celsiusToFahrenheit(celsiusSeekBar.progress)
        celsiusSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val celsiusValue = progress
                val fahrenheitValue = celsiusToFahrenheit(celsiusValue)
                celsiusTextView.text = "$celsiusValue°C"
                fahrenheitTextView.text = "$fahrenheitValue°F"
                fahrenheitSeekBar.progress = fahrenheitValue
                updateInterestingMessage(celsiusValue, interestingTextView)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        fahrenheitSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val fahrenheitValue = progress
                if (fahrenheitValue < 32) {
                    // If progress is below 32°F, snap back to 32°F
                    fahrenheitSeekBar.progress = 32
                } else {
                    // Otherwise, update UI normally
                    val celsiusValue = fahrenheitToCelsius(fahrenheitValue)
                    celsiusTextView.text = "$celsiusValue°C"
                    fahrenheitTextView.text = "$fahrenheitValue°F"
                    celsiusSeekBar.progress = celsiusValue
                    updateInterestingMessage(celsiusValue, interestingTextView)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    private fun celsiusToFahrenheit(celsius: Int): Int {
        return (celsius * 9 / 5) + 32
    }

    private fun fahrenheitToCelsius(fahrenheit: Int): Int {
        return ((fahrenheit - 32) * 5) / 9
    }

    private fun updateInterestingMessage(celsius: Int, textView: TextView) {
        if (celsius <= 20) {
            textView.text = getString(R.string.warm_msg)
        } else {
            textView.text = getString(R.string.cold_msg)
        }
        Log.d("MainActivity", "Interesting message updated: ${textView.text}")
    }
}