package com.reift.storyapp.utils

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

object Animator {

    const val duration = 750L
    val interpolator = OvershootInterpolator()

    fun slideFade(view: View) {
        view.animate().translationX(-1000f).alpha(0f).setDuration(0)
            .withEndAction {
                view.animate().translationX(0f).alpha(1f).setDuration(duration).interpolator = interpolator
            }
    }

    fun topFade(view: View) {
        view.animate().translationY(-1000f).alpha(0f).setDuration(0)
            .withEndAction {
                view.animate().translationY(0f).alpha(1f).setDuration(duration).interpolator = interpolator
            }
    }

    fun bottomFade(view: View) {
        view.animate().translationY(1000f).alpha(0f).setDuration(0)
            .withEndAction {
                view.animate().translationY(0f).alpha(1f).setDuration(duration).interpolator = interpolator
            }
    }
}