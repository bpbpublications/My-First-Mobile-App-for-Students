package com.example.dynamicdicesimulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textResult: TextView = findViewById(R.id.textResult)
        val buttonRoll: Button = findViewById(R.id.btnRoll)
        val imgDice: ImageView = findViewById(R.id.imgDice)

        buttonRoll.setOnClickListener {
            val randomInt = (1..6).random()
            textResult.text = randomInt.toString()
            val drawableResource = when (randomInt) {
                1 -> R.drawable.dice1
                2 -> R.drawable.dice2
                3 -> R.drawable.dice3
                4 -> R.drawable.dice4
                5 -> R.drawable.dice5
                else -> R.drawable.dice6
            }
            imgDice.setImageResource(drawableResource)
        }
    }
}