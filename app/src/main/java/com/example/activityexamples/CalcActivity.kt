package com.example.activityexamples

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calc)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView = findViewById<TextView>(R.id.textView)
        val value1 = intent.getStringExtra("param1")
        val value2 = intent.getStringExtra("param2")

        try {
            val v1 = value1?.toInt()
            val v2 = value2?.toInt()
            val result = v1!! + v2!!
            textView.text = "Param 1: $v1\nParam 2: $v2\nResult: $result"

            intent.putExtra("Result", result)
            setResult(Activity.RESULT_OK, intent)
        } catch (ex: Exception) {
            intent.putExtra("Message", ex.message)
            setResult(Activity.RESULT_CANCELED, intent)
        }

        findViewById<Button>(R.id.button_close).setOnClickListener {
            finish()
        }
    }
}