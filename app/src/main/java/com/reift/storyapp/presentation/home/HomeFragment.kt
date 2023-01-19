package com.reift.storyapp.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.reift.storyapp.R
import com.reift.storyapp.constant.BundleConst
import com.reift.storyapp.databinding.FragmentHomeBinding
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.presentation.detail.DetailBottomFragment
import com.reift.storyapp.presentation.home.adapter.StoryAdapter
import com.reift.storyapp.presentation.home.adapter.StoryRxAdapter
import com.reift.storyapp.presentation.login.LoginActivity
import com.reift.storyapp.presentation.post.PostActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val mDisposable = CompositeDisposable()

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        initView()
        initObserver()
        logoutButton()

        return binding.root
    }

    private fun initView() {
        binding.apply {
            fabPost.setOnClickListener{
                startActivity(Intent(context, PostActivity::class.java))
            }
        }
    }

    private fun logoutButton() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            intentToLogin()
        }
    }

    private fun intentToLogin() {
        startActivity(Intent(context, LoginActivity::class.java))
        requireActivity().finish()
    }

    private fun initObserver() {

        binding.rvStory.apply {
            val mAdapter = StoryRxAdapter{ story ->
                val bundle = Bundle()
                val detailBottomFragment = DetailBottomFragment()
                bundle.putParcelable(BundleConst.DETAIL_DATA, story)
                detailBottomFragment.arguments = bundle
                detailBottomFragment.show(requireActivity().supportFragmentManager, null)
            }
            viewModel.getAllStories()
            mDisposable.add(viewModel.getAllStories().subscribe{
                mAdapter.submitData(lifecycle, it)
            })
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        mDisposable.dispose()

        super.onDestroyView()
    }

}