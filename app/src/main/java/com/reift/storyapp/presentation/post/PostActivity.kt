package com.reift.storyapp.presentation.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reift.storyapp.R
import com.reift.storyapp.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}