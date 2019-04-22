package com.nerdery.slicesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent.hasExtra("com.nerdery.slicesapp.EXTRA_TOAST_MESSAGE")) {
            Toast.makeText(this, intent.getStringExtra("com.nerdery.slicesapp.EXTRA_TOAST_MESSAGE"), Toast.LENGTH_LONG).show()
        }
    }
}
