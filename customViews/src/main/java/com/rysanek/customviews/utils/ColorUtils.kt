package com.rysanek.customviews.utils

import com.rysanek.customviews.theme.ThemeManager.currentTheme

@JvmOverloads
fun getCustomColorString(num: Int, customChoice: String? = null): String? = when (num) {
    1 -> currentTheme.backgroundColor
    2 -> currentTheme.accentColor
    3 -> currentTheme.buttonBgColor
    4 -> currentTheme.buttonTextColor
    5 -> currentTheme.primaryTextColor
    6 -> currentTheme.errorColor
    7 -> currentTheme.white
    8 -> currentTheme.black
    9 -> currentTheme.transparent
    else -> customChoice
}