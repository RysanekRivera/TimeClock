package com.rysanek.timeclock.ui.activities.base

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.rysanek.timeclock.domain.Constants.IDLE_INTERVAL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

abstract class TimeClockActivity: AppCompatActivity() {

    private var timer: Timer? = null
    protected var job: Job? = null
    protected val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        lifecycleScope.launchWhenResumed {

//            launchOnIdleTimer()

        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
//        launchOnIdleTimer()
    }

    private fun launchOnIdleTimer() {
        timer?.cancel()
        timer = null
        timer = Timer()
        val timerTask = object: TimerTask() {
            override fun run() {
                job = CoroutineScope(Main).launch {
                    // Log out user here
                    Toast.makeText(this@TimeClockActivity, "User Signed Out Due to Inactivity", Toast.LENGTH_LONG).show()
                }

                job = null
            }
        }

        timer?.schedule(timerTask, IDLE_INTERVAL)
    }
}