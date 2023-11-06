package com.example.placardoracha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    val startButton: Button = findViewById(R.id.start)
    startButton.setOnClickListener {
      val intent = Intent(this, ScoreboardActivity::class.java)
      startActivity(intent)
    }
  }
}
