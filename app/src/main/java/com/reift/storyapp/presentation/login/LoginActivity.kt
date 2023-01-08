package com.reift.storyapp.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.reift.storyapp.R
import com.reift.storyapp.databinding.ActivityLoginBinding
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.presentation.main.MainActivity
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
        checkUserSession()
        buttonEnablelation()
        validateForm()
        setUpLogin()
    }

    private fun checkUserSession() {
        if (viewModel.isUserLogin()) {
            intentToMain()
        }
    }

    private fun setUpLogin() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                viewModel.loginUser(email, password)
                loginObserver()
            }
        }
    }

    private fun loginObserver() {
        viewModel.loginResponse.observe(this){
            when(it){
                is Resource.Success -> {
                    if(it.data?.isError == false){
                        intentToMain()
                    }
                    Toast.makeText(this, it.data?.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    val message = "Login Error! either wrong email or password"
                    Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun intentToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
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
                finish()
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