package com.michel.analogclock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        findViewById<Button>(R.id.button).setOnClickListener {
            MainActivity.startFrom(this@ExampleActivity)
        }
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, ExampleActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}