package com.reift.storyapp.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.databinding.ItemStoryBinding

class StoryAdapter(val itemClicked: (story: Story) -> Unit): RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    val listStory = arrayListOf<Story>()

    fun setStory(list: List<Story>){
        listStory.clear()
        listStory.addAll(list)
    }

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

                holder.binding.root.setOnClickListener {
                    itemClicked(this)
                }

            }
        }
    }

    override fun getItemCount() = listStory.size
}