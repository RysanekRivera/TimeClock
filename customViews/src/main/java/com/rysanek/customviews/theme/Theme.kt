package com.rysanek.customviews.theme

sealed class Theme(
    open val backgroundColor : String,
    open val accentColor : String,
    open val buttonBgColor : String,
    open val buttonTextColor : String,
    open val primaryTextColor : String,
    open val errorColor : String,
    open val white : String = "#FFFFFF",
    open val black : String = "#000000",
    open val transparent : String = "#00000000"
) {

    data class DogPicTheme1(
        override val backgroundColor: String = "#FFFFFF",
        override val accentColor : String = "#03A9F4",
        override val buttonBgColor : String = "#FF5722",
        override val buttonTextColor : String = "#FFFFFF",
        override val primaryTextColor : String = "#FF5722",
        override val errorColor: String = "#F44336"
    ) : Theme(
        backgroundColor = backgroundColor,
        accentColor = accentColor,
        buttonBgColor = buttonBgColor,
        buttonTextColor = buttonTextColor,
        primaryTextColor = primaryTextColor,
        errorColor = errorColor
    )

    data class DogPicTheme2(
        override val backgroundColor: String = "#c62828",
        override val accentColor : String = "#8e0000",
        override val buttonBgColor : String = "#c62828",
        override val buttonTextColor : String = "#fce4ec",
        override val primaryTextColor : String = "#FFFFFF",
        override val errorColor: String = "#FF3B3099"
    ) : Theme(
        backgroundColor = backgroundColor,
        accentColor = accentColor,
        buttonBgColor = buttonBgColor,
        buttonTextColor = buttonTextColor,
        primaryTextColor = primaryTextColor,
        errorColor = errorColor
    )

}
