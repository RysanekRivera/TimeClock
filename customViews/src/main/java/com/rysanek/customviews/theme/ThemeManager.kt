package com.rysanek.customviews.theme

import android.app.Activity

object ThemeManager {

    @JvmStatic
    var currentTheme : Theme = Theme.DogPicTheme1()

    @JvmStatic
    fun Activity.switchColorTheme() {
        currentTheme = when (currentTheme) {
            is Theme.DogPicTheme1 -> Theme.DogPicTheme2()
            else -> Theme.DogPicTheme1()
        }

        recreate()
    }

}