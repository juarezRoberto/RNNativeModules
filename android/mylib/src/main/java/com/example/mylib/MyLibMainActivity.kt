package com.example.mylib

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mylib.databinding.ActivityMyLibMainBinding

class MyLibMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyLibMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyLibMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myLibButton.setOnClickListener {
            finish()
        }
    }
}