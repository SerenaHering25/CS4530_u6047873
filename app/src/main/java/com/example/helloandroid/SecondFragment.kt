package com.example.helloandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment(R.layout.fragment_second) {
    // called once fragments view has been created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // read text from firstFragment Selected_text
        // if key is missing/no selected return No selection
        val selectedText = arguments?.getString("SELECTED_TEXT") ?: "No selection"

        // find the TextView in layout that will display passed in text
        val textView = view.findViewById<TextView>(R.id.textDisplay)
        textView.text = selectedText // set textView to SELECTED_TEXT

        // find back button in layout
        val backButton = view.findViewById<Button>(R.id.buttonBack)
        backButton.setOnClickListener { // register a click listener
            parentFragmentManager.popBackStack()
        }
    }
}