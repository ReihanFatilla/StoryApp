package com.reift.storyapp.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object Locationutils {
    // nama fungsi harus checkPermission agar tdk muncul warning permission check
    fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}