package com.example.helloandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // load and display activity_main.xml

        // control which fragments are displayed and start a batch of changes to apply together
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                // place firstFragment into fragment_container spot
                .replace(R.id.fragment_container, FirstFragment())
                .commit()
        }
    }
}