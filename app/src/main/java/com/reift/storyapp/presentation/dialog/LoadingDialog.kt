package com.reift.storyapp.presentation.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.reift.storyapp.R


class LoadingDialog internal constructor(
    private val activity: Activity
) {
    private lateinit var dialog: AlertDialog
    @SuppressLint("InflateParams")
    fun startLoadingdialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismissdialog() {
        dialog.dismiss()
    }
}