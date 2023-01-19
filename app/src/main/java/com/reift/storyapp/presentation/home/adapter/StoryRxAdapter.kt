package com.reift.storyapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reift.storyapp.databinding.ItemStoryBinding
import com.reift.storyapp.domain.entity.story.Story


class StoryRxAdapter(val itemClicked: (story: Story) -> Unit) :
    PagingDataAdapter<Story, StoryRxAdapter.StoryViewHolder>(DIFF_CALLBACK) {
    class StoryViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StoryRxAdapter.StoryViewHolder(
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.binding.apply {
            with(getItem(position)) {
                if (this == null) return
                tvCreator.text = name
                tvDescription.text = description

                com.bumptech.glide.Glide.with(imgThumbnail.context)
                    .load(photoUrl)
                    .into(imgThumbnail)

                holder.binding.root.setOnClickListener {
                    itemClicked(this)
                }

            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}