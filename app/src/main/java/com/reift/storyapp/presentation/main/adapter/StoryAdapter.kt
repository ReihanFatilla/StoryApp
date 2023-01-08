package com.reift.storyapp.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reift.storyapp.data.remote.response.story.Story
import com.reift.storyapp.databinding.ItemStoryBinding

class StoryAdapter: RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    val listStory = arrayListOf<Story>()

    class StoryViewHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StoryViewHolder(
        ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.binding.apply {
            with(listStory[position]){
                tvCreator.text = name
                tvDescription.text = description

                Glide.with(imgThumbnail.context)
                    .load(photoUrl)
                    .into(imgThumbnail)
            }
        }
    }

    override fun getItemCount() = listStory.size
}