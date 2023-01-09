package com.reift.storyapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.databinding.ActivityLoginBinding
import com.reift.storyapp.presentation.dialog.LoadingDialog
import com.reift.storyapp.presentation.main.MainActivity
import com.reift.storyapp.presentation.register.RegisterActivity
import com.reift.storyapp.utils.Animator
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModel()

    private var loadingDialog = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
        animateView()
        checkUserSession()
        buttonEnablelation()
        validateForm()
        setUpLogin()
    }

    private fun animateView() {
        binding.apply {
            Animator.slideFade(tilPassword)

            Handler(Looper.getMainLooper()).postDelayed({
                Animator.slideFade(tilEmail)
                Animator.bottomFade(llRegister)
            }, 100)

            Animator.topFade(tvStoryApp)
            Animator.bottomFade(btnLogin)
        }
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

                loadingDialog.startLoadingdialog()
                viewModel.loginUser(email, password).observe(this@LoginActivity){
                    observerLogin(it)
                }
            }
        }
    }

    private fun observerLogin(login: Login?) {
        Toast.makeText(applicationContext, login?.message.toString(), Toast.LENGTH_SHORT).show()
        if(login?.isError == false){
            intentToMain()
        }
        loadingDialog.dismissdialog()
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
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
        }
    }

    private fun validateForm(){
        binding.apply {
            var emailValid = false
            var passwordValid = false
            edtPassword.validateLenght(tilPassword)
                .observe(this@LoginActivity) {
                    passwordValid = it
                    viewModel.buttonEnabled.value = passwordValid && emailValid
                }
            edtEmail.validateFormat(tilEmail).observe(this@LoginActivity) {
                emailValid = it
                viewModel.buttonEnabled.value = passwordValid && emailValid
            }
        }
    }
}