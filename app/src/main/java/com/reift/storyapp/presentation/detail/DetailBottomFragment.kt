package com.reift.storyapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.reift.storyapp.R
import com.reift.storyapp.constant.BundleConst
import com.reift.storyapp.databinding.FragmentDetailBottomBinding
import com.reift.storyapp.domain.entity.story.Story

class DetailBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailBottomBinding

    private var _story: Story? = null
    private val story get() = _story as Story

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBottomBinding.inflate(layoutInflater)

        _story = arguments?.getParcelable(BundleConst.DETAIL_DATA)

        initDetailData()
        initView()

        return binding.root
    }

    private fun initView() {
        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun initDetailData() {
        binding.apply {
            with(story){
                tvCreator.text = name
                tvDescription.text = description
                Glide.with(this@DetailBottomFragment)
                    .load(photoUrl)
                    .into(imgThumbnail)
            }
        }
    }

}