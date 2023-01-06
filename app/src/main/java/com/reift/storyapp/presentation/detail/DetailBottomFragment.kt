package com.reift.storyapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.reift.storyapp.R
import com.reift.storyapp.databinding.FragmentDetailBottomBinding

class DetailBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBottomBinding.inflate(layoutInflater)

        return binding.root
    }

}