package com.reift.storyapp.utils

import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.register.Register
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

    fun generatorDummyRegister(): Register {
        return Register(
            false,
            "Register Success"
        )
    }

    fun generatorDummyLogin(): Login {
        return Login(
            false,
            "Register Success",
            "user123",
            "123,",
            "xs38ms241msda"
        )
    }
}