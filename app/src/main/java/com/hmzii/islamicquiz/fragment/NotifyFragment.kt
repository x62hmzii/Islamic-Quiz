package com.hmzii.islamicquiz.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hmzii.islamicquiz.R

class NotifyFragment : Fragment() {

    private lateinit var historyContainer: LinearLayout
    private val historyItems = mutableListOf<Pair<String, View>>() // List to store history items and their views

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notify, container, false)
        historyContainer = view.findViewById(R.id.historyContainer)

        loadScores()

        return view
    }

    private fun loadScores() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            val scorePreferences: SharedPreferences = requireActivity().getSharedPreferences("scores", Context.MODE_PRIVATE)
            val scores = scorePreferences.getStringSet("scoreSet", setOf())

            scores?.forEach { scoreEntry ->
                addHistoryItem(scoreEntry)
            }
        } else {
            // Optionally show a message that no history is available for guest users
            val noHistoryMessage = TextView(context)
            noHistoryMessage.text = "->No history available for guest users."
            historyContainer.addView(noHistoryMessage)
        }
    }

    private fun addHistoryItem(score: String) {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_history, historyContainer, false)

        val scoreText = itemView.findViewById<TextView>(R.id.scoreText)
        val deleteButton = itemView.findViewById<Button>(R.id.deleteButton)

        scoreText.text = score
        deleteButton.setOnClickListener {
            historyContainer.removeView(itemView)
            // Remove the item from the list
            historyItems.removeIf { it.second == itemView }
            removeScore(score)
        }

        // Add the item view to the container and list
        historyContainer.addView(itemView)
        historyItems.add(score to itemView)
    }

    private fun removeScore(scoreEntry: String) {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("scores", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val scores = sharedPreferences.getStringSet("scoreSet", mutableSetOf())
        scores?.remove(scoreEntry)

        editor.putStringSet("scoreSet", scores)
        editor.apply()
    }
}