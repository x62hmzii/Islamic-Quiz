package com.hmzii.islamicquiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hmzii.brainquiz.QuestionModel
import com.hmzii.islamicquiz.databinding.ActivityQuizBinding
import kotlin.collections.ArrayList

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private var count: Int = 0
    private var score = 0
    private var list: ArrayList<QuestionModel> = ArrayList()
    private var quizQuestions: ArrayList<QuestionModel> = ArrayList()
    private var isAnswered = false
    private var isGuest: Boolean = false
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize vibrator
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Get the value of whether the user is a guest
        isGuest = intent.getBooleanExtra("IS_GUEST", false)

        // Disable option buttons until questions are loaded
        disableOptionButtons()

        Firebase.firestore.collection("quiz")
            .get()
            .addOnSuccessListener { doct ->
                list.clear()
                for (i in doct.documents) {
                    val questionModel = i.toObject(QuestionModel::class.java)
                    questionModel?.let { list.add(it) }
                }
                if (list.isNotEmpty()) {
                    quizQuestions = list.shuffled().take(10) as ArrayList<QuestionModel>
                    loadQuestion()
                    // Enable option buttons after loading questions
                    enableOptionButtons(true)
                } else {
                    Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error getting questions: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        binding.option1.setOnClickListener { handleOptionClick(binding.option1) }
        binding.option2.setOnClickListener { handleOptionClick(binding.option2) }
        binding.option3.setOnClickListener { handleOptionClick(binding.option3) }
        binding.option4.setOnClickListener { handleOptionClick(binding.option4) }
    }

    private fun loadQuestion() {
        if (count < quizQuestions.size) {
            val question = quizQuestions[count]
            binding.question.text = question.question
            binding.option1.text = question.option1
            binding.option2.text = question.option2
            binding.option3.text = question.option3
            binding.option4.text = question.option4
            resetOptionButtons()
            isAnswered = false
        }
    }

    private fun handleOptionClick(selectedOption: Button) {
        if (!isAnswered) {
            isAnswered = true
            val question = quizQuestions[count]
            if (selectedOption.text == question.ans) {
                score++
                selectedOption.setBackgroundColor(getColor(R.color.correct_option))
            } else {
                selectedOption.setBackgroundColor(getColor(R.color.incorrect_option))
                question.ans?.let { highlightCorrectOption(it) }
                vibrateOnWrongAnswer() // Vibrate on wrong answer
            }
            count++
            selectedOption.postDelayed({
                if (count >= quizQuestions.size) {
                    if (!isGuest) {
                        saveScore()
                    }
                    val intent = Intent(this, ScoreActivity::class.java)
                    intent.putExtra("SCORE", score)
                    startActivity(intent)
                    finish()
                } else {
                    loadQuestion()
                }
            }, 1000)
        }
    }

    private fun vibrateOnWrongAnswer() {
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(500) // Vibrate for 500 milliseconds
        }
    }

    private fun saveScore() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Retrieve the current set of scores, if any
        val scores = sharedPreferences.getStringSet("scoreSet", mutableSetOf()) ?: mutableSetOf()

        // Create a mutable copy of the set
        val mutableScores = scores.toMutableSet()
        // Add the new score to the mutable set
        mutableScores.add("Score: $score")

        // Save the updated set back to SharedPreferences
        editor.putStringSet("scoreSet", mutableScores)
        editor.apply()
    }

    private fun highlightCorrectOption(correctAnswer: String) {
        if (binding.option1.text == correctAnswer) {
            binding.option1.setBackgroundColor(getColor(R.color.correct_option))
        } else if (binding.option2.text == correctAnswer) {
            binding.option2.setBackgroundColor(getColor(R.color.correct_option))
        } else if (binding.option3.text == correctAnswer) {
            binding.option3.setBackgroundColor(getColor(R.color.correct_option))
        } else if (binding.option4.text == correctAnswer) {
            binding.option4.setBackgroundColor(getColor(R.color.correct_option))
        }
    }

    private fun resetOptionButtons() {
        binding.option1.setBackgroundColor(getColor(R.color.default_option_background))
        binding.option2.setBackgroundColor(getColor(R.color.default_option_background))
        binding.option3.setBackgroundColor(getColor(R.color.default_option_background))
        binding.option4.setBackgroundColor(getColor(R.color.default_option_background))
    }

    private fun enableOptionButtons(enable: Boolean) {
        binding.option1.isEnabled = enable
        binding.option2.isEnabled = enable
        binding.option3.isEnabled = enable
        binding.option4.isEnabled = enable
    }

    private fun disableOptionButtons() {
        binding.option1.isEnabled = false
        binding.option2.isEnabled = false
        binding.option3.isEnabled = false
        binding.option4.isEnabled = false
    }
}