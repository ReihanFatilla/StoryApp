package com.reift.storyapp.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.reift.storyapp.databinding.ActivityMainBinding
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.presentation.main.adapter.StoryAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()

    }

    private fun initObserver() {
        viewModel.storyResponse.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.rvStory.apply {
                        val mAdapter = StoryAdapter()
                        adapter = mAdapter
                        layoutManager = LinearLayoutManager(applicationContext)
                        resource.data?.let { story -> mAdapter.setStory(story) }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(applicationContext, resource.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}