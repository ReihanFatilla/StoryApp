package com.reift.storyapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reift.storyapp.R
import com.reift.storyapp.constant.BundleConst
import com.reift.storyapp.databinding.ActivityMainBinding
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.presentation.detail.DetailBottomFragment
import com.reift.storyapp.presentation.home.HomeViewModel
import com.reift.storyapp.presentation.login.LoginActivity
import com.reift.storyapp.presentation.home.adapter.StoryAdapter
import com.reift.storyapp.presentation.post.PostActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)


    }


}