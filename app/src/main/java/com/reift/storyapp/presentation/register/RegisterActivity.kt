package com.reift.storyapp.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.reift.storyapp.databinding.ActivityRegisterBinding
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.presentation.login.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModel()

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
        validateForm()
        buttonEnablelation()
        setUpRegister()
    }

    private fun buttonEnablelation() {
        viewModel.buttonEnabled.observe(this){
            binding.btnRegister.isEnabled = it
        }
    }

    private fun setUpRegister() {
        binding.apply {
            btnRegister.setOnClickListener {
                val username = edtUsername.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.registerUser(username, email, password)

                registerObserver()
            }
        }
    }

    private fun registerObserver() {
        viewModel.registerResponse.observe(this@RegisterActivity){
            when(it){
                is Resource.Success -> {
                    if(it.data?.isError == false){
                        intentToLogin()
                    }
                    Toast.makeText(this@RegisterActivity, it.data?.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    val message = "Register Error! either email is taken or invalid email format"
                    Toast.makeText(this@RegisterActivity, message,Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun intentToLogin(){
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }

    private fun setUpView() {
        binding.apply {
            tvLogin.setOnClickListener{
                intentToLogin()
            }
            finish()
        }
    }

    private fun validateForm(){
        binding.apply {
            edtPassword.validateLenght(tilPassword).observe(this@RegisterActivity){
                viewModel.buttonEnabled.value = it == true
            }
            edtEmail.validateFormat(tilEmail).observe(this@RegisterActivity){
                viewModel.buttonEnabled.value = it == true
            }
            edtUsername.validateUsername(tilUsername).observe(this@RegisterActivity){
                viewModel.buttonEnabled.value = it == true
            }
        }
    }
}