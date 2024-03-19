package com.michel.analogclock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WorkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        findViewById<Button>(R.id.button).setOnClickListener {
            ExampleActivity.startFrom(this@WorkActivity)
        }
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, WorkActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}