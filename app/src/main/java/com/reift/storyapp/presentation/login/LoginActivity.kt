package com.reift.storyapp.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reift.storyapp.R
import com.reift.storyapp.databinding.ActivityLoginBinding
import com.reift.storyapp.presentation.register.RegisterActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
        buttonEnablelation()
        validateForm()
    }

    private fun buttonEnablelation() {
        viewModel.buttonEnabled.observe(this){
            binding.btnLogin.isEnabled = it
        }
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
            edtPassword.validateLenght(tilPassword).observe(this@LoginActivity){
                viewModel.buttonEnabled.value = it == true
            }
            edtEmail.validateFormat(tilEmail).observe(this@LoginActivity){
                viewModel.buttonEnabled.value = it == true
            }
        }
    }
}