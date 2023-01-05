package com.reift.storyapp.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reift.storyapp.databinding.ActivityRegisterBinding
import com.reift.storyapp.presentation.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
        validateForm()
    }

    private fun setUpView() {
        binding.apply {
            tvLogin.setOnClickListener{
                startActivity(
                    Intent(this@RegisterActivity, LoginActivity::class.java)
                )
            }
        }
    }

    private fun validateForm(){
        binding.apply {
            edtPassword.validateLenght(tilPassword)
            edtEmail.validateFormat(tilEmail)
            edtPasswordConfirm.validateConfirmPassword(tilPasswordConfirm, edtPassword)
        }
    }
}