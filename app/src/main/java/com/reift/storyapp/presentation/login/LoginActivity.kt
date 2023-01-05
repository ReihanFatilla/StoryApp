package com.reift.storyapp.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reift.storyapp.R
import com.reift.storyapp.databinding.ActivityLoginBinding
import com.reift.storyapp.presentation.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
        validateForm()
    }

    private fun setUpView() {
        binding.apply {
            tvRegister.setOnClickListener{
                startActivity(
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                )
            }
        }
    }

    private fun validateForm(){
        binding.apply {
            edtPassword.validateLenght(tilPassword)
            edtEmail.validateFormat(tilEmail)
        }
    }
}