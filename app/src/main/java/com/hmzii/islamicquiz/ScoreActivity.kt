package com.hmzii.islamicquiz

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hmzii.islamicquiz.databinding.ActivityScoreBinding
import java.text.SimpleDateFormat
import java.util.*

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("SCORE", 0)
        binding.score.text = "Your Score is  $score/10"

        saveScore(score)
    }

    private fun saveScore(score: Int) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val scoreEntry = "Score: $score at $timestamp"

        val scores = sharedPreferences.getStringSet("scoreSet", mutableSetOf())
        scores?.add(scoreEntry)

        editor.putStringSet("scoreSet", scores)
        editor.apply()
    }
}