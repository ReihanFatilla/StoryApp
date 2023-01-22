package com.reift.storyapp.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reift.storyapp.data.local.room.dao.StoryDao
import com.reift.storyapp.data.local.room.entity.StoryEntity

@Database(entities = [StoryEntity::class], version = 2, exportSchema = false)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
}