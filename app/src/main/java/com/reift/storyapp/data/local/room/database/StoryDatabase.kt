package com.reift.storyapp.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reift.storyapp.data.local.room.dao.RemoteKeysDao
import com.reift.storyapp.data.local.room.dao.StoryDao
import com.reift.storyapp.data.local.room.entity.RemoteKeys
import com.reift.storyapp.data.local.room.entity.StoryEntity

@Database(entities = [StoryEntity::class, RemoteKeys::class], version = 2, exportSchema = false)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}