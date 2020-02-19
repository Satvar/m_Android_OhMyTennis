package com.tech.cloudnausor.ohmytennis.controller

import androidx.appcompat.app.AppCompatActivity

import com.tech.cloudnausor.ohmytennis.R

import java.lang.ref.WeakReference

/**
 * A convenience class to handle displaying error dialogs.
 */
class ErrorDialogHandler(activity: AppCompatActivity) {

    private val activityRef: WeakReference<AppCompatActivity> = WeakReference(activity)

    fun show(errorMessage: String) {
        val activity = activityRef.get() ?: return

        com.tech.cloudnausor.ohmytennis.dialog.ErrorDialogFragment.newInstance(activity.getString(R.string.validationErrors), errorMessage)
            .show(activity.supportFragmentManager, "error")
    }
}
