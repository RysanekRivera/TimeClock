package com.rysanek.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatEditText
import com.rysanek.customviews.theme.ThemeManager.currentTheme
import com.rysanek.customviews.utils.ColorExtUtils.toColor
import com.rysanek.customviews.utils.getCustomColorString
import kotlin.properties.Delegates

class ColorEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attributeSet, defStyleAttr) {

    private var underlineColor by Delegates.notNull<Int>()
    private var handlesColor by Delegates.notNull<Int>()
    private var cursorColor by Delegates.notNull<Int>()

    init {

        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ColorEditText,
            0,
            0
        )

        setTextColor(getCustomColorString(attrs.getInt(R.styleable.ColorEditText_colorEditTextTextColor, 0), currentTheme.primaryTextColor)!!.toColor())

        underlineColor = getCustomColorString(attrs.getInt(R.styleable.ColorEditText_colorEditTextUnderlineColor, 2), currentTheme.primaryTextColor)!!.toColor()

        backgroundTintList = ColorStateList.valueOf(underlineColor)

        handlesColor =  getCustomColorString(attrs.getInt(R.styleable.ColorEditText_colorEditTextSelectedHandleColor, 0), currentTheme.primaryTextColor)!!.toColor()

        setSelectedTextHandleColor(handlesColor)

        setCursorDrawable(attrs)

        val highlightAlpha = attrs.getInt(R.styleable.ColorEditText_colorEditTextHighlightColorAlpha, 80)

        val hintTextAlpha = attrs.getInt(R.styleable.ColorEditText_colorEditTextHinTextAlpha, 80)

        val hLightColor = getCustomColorString(attrs.getInt(R.styleable.ColorEditText_colorEditTextHighlightColor, 0), currentTheme.primaryTextColor)!!.toColor(highlightAlpha)

        val hLightTextColor = getCustomColorString(attrs.getInt(R.styleable.ColorEditText_colorEditTextHighlightColor, 0), currentTheme.primaryTextColor)!!.toColor(hintTextAlpha)

        highlightColor = hLightColor

        setHintTextColor(hLightTextColor)

        attrs.recycle()

        invalidate()

        requestLayout()
    }

    private fun setSelectedTextHandleColor(color : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textSelectHandle?.setTint(color)
            textSelectHandleLeft?.setTint(color)
            textSelectHandleRight?.setTint(color)
        }
    }

    private fun setCursorDrawable(attrs : TypedArray) {

        cursorColor = getCustomColorString(attrs.getInt(R.styleable.ColorEditText_colorEditTextCursorColor, 0), currentTheme.primaryTextColor)!!.toColor()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            textCursorDrawable?.mutate()?.setTint(cursorColor)

            textCursorDrawable?.invalidateSelf()

        }

    }

    fun resetColorState() {
        backgroundTintList = ColorStateList.valueOf(underlineColor)

        setSelectedTextHandleColor(handlesColor)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            textCursorDrawable?.mutate()?.setTint(cursorColor)

            textCursorDrawable?.invalidateSelf()

        }

        invalidate()

        requestLayout()
    }

}