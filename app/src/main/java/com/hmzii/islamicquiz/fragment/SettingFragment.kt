package com.hmzii.islamicquiz.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hmzii.islamicquiz.LoginActivity
import com.hmzii.islamicquiz.R
import com.hmzii.islamicquiz.AboutActivity

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        // Reference to the user email TextView
        val userEmailTextView: TextView = view.findViewById(R.id.userEmailTextView)

        // Fetch the currently logged-in user
        val currentUser = Firebase.auth.currentUser
        currentUser?.let {
            // Set the user email to the TextView
            userEmailTextView.text = it.email
        }

        // Reference to the logout button
        val logoutButton: Button = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            logout()
        }

        // Reference to the about TextView
        val aboutTextView: TextView = view.findViewById(R.id.about)
        aboutTextView.setOnClickListener {
            openAbout()
        }

        // Reference to the invite TextView
        val inviteTextView: TextView = view.findViewById(R.id.inviteTextView)
        inviteTextView.setOnClickListener {
            shareApp()
        }

        // Reference to the privacy policy TextView
        val privacyPolicyTextView: TextView = view.findViewById(R.id.privacyPolicyTextView)
        privacyPolicyTextView.setOnClickListener {
            openPrivacyPolicy()
        }

        return view
    }

    private fun logout() {
        // Clear login state
        val sharedPref = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("is_logged_in", false)
            apply()
        }

        // Sign out from Firebase
        Firebase.auth.signOut()

        // Navigate to LoginActivity
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun openAbout() {
        // Navigate to AboutActivity
        val intent = Intent(requireActivity(), AboutActivity::class.java)
        startActivity(intent)
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareMessage = "Check out this amazing Islamic Quiz App: [https://play.google.com/store/apps/details?id=com.hmzii.islamicquiz]"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun openPrivacyPolicy() {
        // Navigate to Privacy Policy URL
        val url = "https://x62hmzii.github.io/privacy-policy/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}