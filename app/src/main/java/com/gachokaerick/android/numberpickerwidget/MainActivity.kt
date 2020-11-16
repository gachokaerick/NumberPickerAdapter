package com.gachokaerick.android.numberpickerwidget

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.gachokaerick.android.numberpicker.NumberPickerRecyclerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview = findViewById(R.id.rvNumbers)
        val adapter = NumberPickerRecyclerAdapter(
            this, getListOfNumbers(1, 50),
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_black_card,
                null
            ),
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_white_card,
                null
            ),
            "#ffffff",
            "#000000",
            { view, item -> numberSelected(item) },
            tvTextSize = 12
        )
        recyclerview.adapter = adapter
    }

    private fun getListOfNumbers(from: Int, to: Int, reversed: Boolean = false): List<String> {
        val list = mutableListOf<String>()
        for (num in from..to) {
            list.add("$num")
        }
        return if (reversed) {
            list.reversed()
        } else {
            list
        }
    }

    private fun numberSelected(item: String?) {
        if (item != null) {
            Toast.makeText(this, item, Toast.LENGTH_SHORT)
                .show()
        }
    }
}