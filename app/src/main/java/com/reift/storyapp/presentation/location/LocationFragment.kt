package com.reift.storyapp.presentation.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reift.storyapp.R
import com.reift.storyapp.databinding.FragmentLocationBinding


class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }
}