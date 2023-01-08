package com.reift.storyapp.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reift.storyapp.databinding.ActivityRegisterBinding
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