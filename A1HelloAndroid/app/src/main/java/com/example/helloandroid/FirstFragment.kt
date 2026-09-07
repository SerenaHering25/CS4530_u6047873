package com.example.helloandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonIds = listOf(
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5
        )
        // loop through button IDs and pass text to navigation fun
        for (id in buttonIds) {
            val button = view.findViewById<Button>(id)
            button.setOnClickListener {
                navigateToSecondFragment(button.text.toString())
            }
        }
    }
    // Handles swapping firstFragment for secondFragment
    private fun navigateToSecondFragment(selectedText: String) {
        val secondFragment = SecondFragment()
        // use a Bundle to pass the button text
        val bundle = Bundle()
        // store selected button for secondFragment lookup
        bundle.putString("SELECTED_TEXT", selectedText)
        secondFragment.arguments = bundle
        // start a batch of changes to apply
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, secondFragment) //replace fragment_container w/ 2nd fragment
            .addToBackStack(null) //let user hit back button
            .commit()
    }
}