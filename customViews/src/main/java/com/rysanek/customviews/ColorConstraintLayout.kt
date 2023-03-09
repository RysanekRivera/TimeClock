package com.rysanek.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.rysanek.customviews.theme.ThemeManager.currentTheme
import com.rysanek.customviews.utils.ColorExtUtils.toColor
import com.rysanek.customviews.utils.getCustomColorString

class ColorConstraintLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val colorOfBackground: String?
    private var colorOfStroke: String? = null
    private val widthOfStroke: Int
    private val radiusOfCorners: Float
    private val alphaOfBackground: Int

    init {

        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ColorConstraintLayout,
            0,
            0
        )

        colorOfBackground = getCustomColorString(
            attrs.getInt(
                R.styleable.ColorConstraintLayout_colorConstraintLayoutBackgroundColor,
                0
            )
        )

        getCustomColorString(
            attrs.getInt(
                R.styleable.ColorConstraintLayout_colorConstraintLayoutStrokeColor,
                0
            ), currentTheme.accentColor
        )?.let { colorOfStroke = it }

        widthOfStroke =
            attrs.getInt(R.styleable.ColorConstraintLayout_colorConstraintLayoutStrokeWidth, 0)

        radiusOfCorners =
            attrs.getFloat(R.styleable.ColorConstraintLayout_colorConstraintLayoutCornerRadius, 0f)

        alphaOfBackground =
            attrs.getInt(R.styleable.ColorConstraintLayout_colorConstraintLayoutBackgroundAlpha, -1)

        colorOfStroke = getCustomColorString(attrs.getInt(R.styleable.ColorConstraintLayout_colorConstraintLayoutStrokeColor, 0))

        setColorOfBackground()

        attrs.recycle()

    }

    @JvmOverloads
    fun setColorOfBackground(
        strokeColor: String? = colorOfStroke,
        bgColor: String? = colorOfBackground,
        bgAlpha: Int = alphaOfBackground,
        strokeWidth: Int? = widthOfStroke,
        cornerRad: Float? = radiusOfCorners
    ) {
        background = GradientDrawable().apply {

            bgColor?.let { bg ->
                if (bgAlpha > -1) {
                    mutate()
                    setColor(bg.toColor(bgAlpha))
                } else {
                    mutate()
                    setColor(bg.toColor())
                }
            }

            strokeColor?.let { setStroke(strokeWidth ?: widthOfStroke, ColorStateList.valueOf(it.toColor())) }

            shape = GradientDrawable.RECTANGLE

            cornerRadius = cornerRad ?: radiusOfCorners

        }

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

    fun resetColorState() {
        setColorOfBackground()
    }

}