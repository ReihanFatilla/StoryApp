package com.reift.storyapp.utils

import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.story.Story

object DummyData {
    fun generateDummyStory(): List<Story> {
        val listStory = ArrayList<Story>()
        for (i in 0..10) {
            val news = Story(
                "https://www.google.com",
                "https://www.google.com",
                "https://www.google.com",
                "https://www.google.com",
                "https://www.google.com",
            )
            listStory.add(news)
        }
        return listStory
    }

    fun generateError(): Resource<List<Story>> {
        return Resource.Error("Error Network")
    }
}