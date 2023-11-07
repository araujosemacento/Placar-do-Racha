package com.example.placardoracha

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreboardActivity : AppCompatActivity() {

  private lateinit var timer: CountDownTimer
  private var second = 0
  private var isTimerRunning = true
  var lastScoreUpdateTime: Long = 0
  var storedScore: Array<Int> = arrayOf(0, 0)
  var currentScore: Array<Int> = arrayOf(0, 0)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.scoreboard_layout)

    val timeTextView = findViewById<TextView>(R.id.time)
    val pauseButton = findViewById<ImageButton>(R.id.pausebutton)
    val undoButton = findViewById<ImageButton>(R.id.undobutton)

    val score1Label = findViewById<Button>(R.id.score1label)
    val team1Score = findViewById<Button>(R.id.team1score)
    val score2Label = findViewById<Button>(R.id.score2label)
    val team2Score = findViewById<Button>(R.id.team2score)

    val homeButton: ImageButton = findViewById(R.id.imageButton)

    homeButton.setOnClickListener {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }

    currentScore = arrayOf(team1Score.text.toString().toInt(), team2Score.text.toString().toInt())

    score1Label.setOnClickListener {
      val score = team1Score.text.toString().toInt()
      team1Score.text = String.format("%02d", score + 1)
      currentScore[0] = score + 1
      lastScoreUpdateTime = System.currentTimeMillis()
    }

    team1Score.setOnClickListener {
      val score = team1Score.text.toString().toInt()
      team1Score.text = String.format("%02d", score + 1)
      currentScore[0] = score + 1
      lastScoreUpdateTime = System.currentTimeMillis()
    }

    score2Label.setOnClickListener {
      val score = team2Score.text.toString().toInt()
      team2Score.text = String.format("%02d", score + 1)
      currentScore[1] = score + 1
      lastScoreUpdateTime = System.currentTimeMillis()
    }

    team2Score.setOnClickListener {
      val score = team2Score.text.toString().toInt()
      team2Score.text = String.format("%02d", score + 1)
      currentScore[1] = score + 1
      lastScoreUpdateTime = System.currentTimeMillis()
    }

    timer =
        object : CountDownTimer(Long.MAX_VALUE, 1000) {
          var second = 0

          override fun onTick(millisUntilFinished: Long) {
            second++
            val minutes = second / 60
            val seconds = second % 60
            timeTextView.text = String.format("%02d:%02d", minutes, seconds)
          }

          override fun onFinish() {}
        }

    timer.start()

    pauseButton.setOnClickListener {
      if (isTimerRunning) {
        timer.cancel()
      } else {
        timer.start()
      }
      isTimerRunning = !isTimerRunning
    }

    if (savedInstanceState != null) {
      second = savedInstanceState.getInt("second")
      isTimerRunning = savedInstanceState.getBoolean("isTimerRunning")
      timeTextView.text = second.toString()
      if (isTimerRunning) {
        timer.start()
      }
    }

    undoButton.setOnClickListener {
      val currentTime = System.currentTimeMillis()
      val timeDifference = currentTime - lastScoreUpdateTime
      if (timeDifference > 250 && timeDifference < 500) {
        storedScore[0] = currentScore[0]
        storedScore[1] = currentScore[1]
        lastScoreUpdateTime = currentTime
      } else if (timeDifference >= 500) {
        currentScore[0] = storedScore[0]
        currentScore[1] = storedScore[1]
        team1Score.text = String.format("%02d", currentScore[0])
        team2Score.text = String.format("%02d", currentScore[1])
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    timer.cancel()
  }

  override fun onStart() {
    super.onStart()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putIntArray("currentScore", currentScore.toIntArray())
    outState.putIntArray("storedScore", storedScore.toIntArray())
    outState.putLong("lastScoreUpdateTime", lastScoreUpdateTime)
    outState.putInt("second", second)
    outState.putBoolean("isTimerRunning", isTimerRunning)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    currentScore = savedInstanceState.getIntArray("currentScore")?.toTypedArray() ?: arrayOf(0, 0)
    storedScore = savedInstanceState.getIntArray("storedScore")?.toTypedArray() ?: arrayOf(0, 0)
    lastScoreUpdateTime = savedInstanceState.getLong("lastScoreUpdateTime")
    second = savedInstanceState.getInt("second")
    isTimerRunning = savedInstanceState.getBoolean("isTimerRunning")
    super.onRestoreInstanceState(savedInstanceState)
  }
}
