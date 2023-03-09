package com.rysanek.customviews

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.DrawableCompat
import com.rysanek.customviews.theme.ThemeManager.currentTheme
import com.rysanek.customviews.utils.ColorExtUtils.toColor
import com.rysanek.customviews.utils.getCustomColorString

class ColorImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {

    init {

        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ColorImageView,
            0,
            0
        )

        val maskColor = getCustomColorString(attrs.getInt(R.styleable.ColorImageView_colorImageViewColor, 0), currentTheme.white)!!

        val maskColorAlpha = attrs.getInt(R.styleable.ColorImageView_colorImageViewAlpha, -1)

        val finalColor = if (maskColorAlpha == -1) {
            maskColor.toColor()
        } else {
            maskColor.toColor(maskColorAlpha)
        }

        drawable?.let { icon ->
            icon.mutate()
            DrawableCompat.setTint(icon, finalColor)
            DrawableCompat.setTintMode(icon, PorterDuff.Mode.SRC_IN)

        }

        attrs.recycle()

        invalidate()

        requestLayout()
    }

    override fun setOnClickListener(l: OnClickListener?) {

        super.setOnClickListener { v ->
            v.isEnabled = false
            l?.onClick(v)
            v.isEnabled = true
        }

    }

    fun setCustomColor(customColor: String) {

        drawable?.let { icon ->
            icon.mutate()
            DrawableCompat.setTint(icon, customColor.toColor())
            DrawableCompat.setTintMode(icon, PorterDuff.Mode.SRC_IN)

        }

        invalidate()

        requestLayout()
    }


}