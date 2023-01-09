package com.reift.storyapp.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.databinding.ActivityRegisterBinding
import com.reift.storyapp.presentation.login.LoginActivity
import com.reift.storyapp.utils.Animator
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModel()

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
        animateView()
        validateForm()
        buttonEnablelation()
        setUpRegister()
    }

    private fun animateView() {
        binding.apply {
            Animator.slideFade(tilPassword)

            Handler(Looper.getMainLooper()).postDelayed({
                Animator.slideFade(tilEmail)
                Animator.bottomFade(llLogin)
                Handler(Looper.getMainLooper()).postDelayed({
                    Animator.slideFade(tilUsername)
                }, 100)
            }, 100)

            Animator.topFade(tvStoryApp)
            Animator.bottomFade(btnRegister)
        }
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

                viewModel.registerUser(username, email, password).observe(this@RegisterActivity){
                    observeRegister(it)
                }

            }
        }
    }

    private fun observeRegister(register: Register?) {
        Toast.makeText(applicationContext, register?.message.toString(), Toast.LENGTH_SHORT).show()
        if(register?.isError == false){
            intentToLogin()
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
                finish()
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