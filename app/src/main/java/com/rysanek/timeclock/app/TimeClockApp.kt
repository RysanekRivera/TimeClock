package com.rysanek.timeclock.app

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraXConfig
import com.google.firebase.FirebaseApp
import com.rysanek.customviews.theme.ThemeManager
import com.rysanek.customviews.utils.ColorExtUtils.toColor
import com.rysanek.timeclock.ui.activities.MainActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimeClockApp : Application(), Application.ActivityLifecycleCallbacks, CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        FirebaseApp.initializeApp(this)
    }

    override fun getCameraXConfig(): CameraXConfig {
        return CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig())
            .setAvailableCamerasLimiter(CameraSelector.DEFAULT_FRONT_CAMERA)
            .build()
    }

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)

        try {
            Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
                // Log the Exception
                Log.d(activity::class.simpleName, "Error on Thread: ${thread.name}\nMessage: ${exception.message}")
                resetActivityOnUnhandledException(activity)
            }
        } catch (e: Exception) {
            Log.d(activity::class.simpleName, "Error Setting Uncaught Exception Handler")
            resetActivityOnUnhandledException(activity)
        }

        activity.window?.setBackgroundDrawable(ColorDrawable(ThemeManager.currentTheme.backgroundColor.toColor()))
        activity.window?.statusBarColor = ThemeManager.currentTheme.accentColor.toColor()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    private fun resetActivityOnUnhandledException(activity: Activity) {

        Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
            activity.finish()
        }

    }

}