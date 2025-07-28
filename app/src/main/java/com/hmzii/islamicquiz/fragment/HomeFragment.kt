package com.hmzii.islamicquiz.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hmzii.islamicquiz.QuizActivity
import com.hmzii.islamicquiz.R
import android.widget.Button

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find the button by its ID
        val startButton: Button = view.findViewById(R.id.startbutton)

        // Set a click listener on the button
        startButton.setOnClickListener {
            // Create an intent to start QuizActivity
            val intent = Intent(activity, QuizActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}