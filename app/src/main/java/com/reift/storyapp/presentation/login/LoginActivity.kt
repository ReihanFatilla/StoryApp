package com.reift.storyapp.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reift.storyapp.R
import com.reift.storyapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            edtPassword.validateLenght(tilPassword)
        }
    }
}