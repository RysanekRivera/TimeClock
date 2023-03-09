package com.rysanek.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.rysanek.customviews.theme.ThemeManager.currentTheme
import com.rysanek.customviews.utils.ColorExtUtils.toColor
import com.rysanek.customviews.utils.getCustomColorString

class ColorTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributeSet, defStyleAttr) {
    
    
    init {
    
        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ColorTextView,
            0,
            0
        )
    
        setTextColor(
            getCustomColorString(attrs.getInt(R.styleable.ColorTextView_colorTextViewColor, 0), currentTheme.primaryTextColor)!!.toColor()
        )
    
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
    
    fun toPrimaryTextColor() {
        
        setTextColor(currentTheme.primaryTextColor.toColor())
        
        invalidate()
        
        requestLayout()
        
    }
    
    fun toErrorColor() {
        
        setTextColor(currentTheme.errorColor.toColor())
        
        invalidate()
        
        requestLayout()
        
    }
}