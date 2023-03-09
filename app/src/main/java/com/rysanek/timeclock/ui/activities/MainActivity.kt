package com.rysanek.timeclock.ui.activities

import android.os.Bundle
import com.rysanek.timeclock.databinding.ActivityMainBinding
import com.rysanek.timeclock.ui.activities.base.TimeClockActivity

class MainActivity : TimeClockActivity() {

    private lateinit var layout: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = ActivityMainBinding.inflate(layoutInflater)

        setContentView(layout.root)

    }


}

