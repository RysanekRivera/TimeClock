package com.rysanek.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.rysanek.customviews.theme.ThemeManager.currentTheme
import com.rysanek.customviews.utils.ColorExtUtils.toColor
import com.rysanek.customviews.utils.ColorExtUtils.transparency
import com.rysanek.customviews.utils.getCustomColorString

class ColorButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attributeSet, defStyleAttr) {
    
    private val colorOfBackground: String?
    private var colorOfStroke: String? = null
    private var colorOfText: String
    private val widthOfStroke: Int
    private val radiusOfCorners: Float
    private val alphaOfBackground: Int
    private var defaultElevation: Float = 5f
    private var rippleColor: String
    private var rippleAlpha: Int? = null
    
    init {
    
        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ColorButton,
            0,
            0
        )
        
        val style = attrs.getInt(R.styleable.ColorButton_colorButtonStyle, 1)
    
        colorOfBackground = getCustomColorString(
            attrs.getInt(R.styleable.ColorButton_colorButtonBackgroundColor, 0),
            when (style) {
                1, 2->  currentTheme.buttonBgColor
                else -> currentTheme.transparent
            }
        )
    
        getCustomColorString(attrs.getInt(
            R.styleable.ColorButton_colorButtonStrokeColor, 0),
            currentTheme.buttonBgColor
        )?.let { colorOfStroke = it }
    
        val strWidth = attrs.getInt(R.styleable.ColorButton_colorButtonStrokeWidth, 0)
        
        widthOfStroke = when (style) {
            3, 4 -> if (strWidth != 0) strWidth else 5
            else -> strWidth
        }
        
        val cornerRad = attrs.getFloat(R.styleable.ColorButton_colorButtonCornerRadius, 90f)
    
        radiusOfCorners = when (style) {
            1, 3 -> if (cornerRad != 90f) cornerRad else 90f
            2, 4 -> if (cornerRad != 90f) cornerRad else 15f
            else -> cornerRad
        }
        
        alphaOfBackground =
            attrs.getInt(R.styleable.ColorButton_colorButtonBackgroundAlpha, -1)
    
        val strokeColor = getCustomColorString(attrs.getInt(R.styleable.ColorButton_colorButtonStrokeColor, 0))
        
        colorOfStroke = when (style) {
            3, 4 -> strokeColor ?: currentTheme.accentColor
            else -> strokeColor
        }
        
        rippleAlpha = attrs.getInt(R.styleable.ColorButton_colorButtonRippleAlpha, 100)
        
        rippleColor = if (rippleAlpha != null){
            getCustomColorString(attrs.getInt(R.styleable.ColorButton_colorButtonRippleColor, 0), currentTheme.buttonBgColor)!!.transparency(rippleAlpha!!)
        } else {
            getCustomColorString(attrs.getInt(R.styleable.ColorButton_colorButtonRippleColor, 0), currentTheme.buttonBgColor)!!
        }
        
        val colorText = getCustomColorString(attrs.getInt(R.styleable.ColorButton_colorButtonTextColor, 0))
        
        colorOfText = when (style) {
            1, 2 -> colorText ?: currentTheme.white
            3, 4 -> colorText ?: currentTheme.buttonBgColor
            else -> colorText ?: currentTheme.primaryTextColor
        }
    
        setTextColor(colorOfText.toColor())
    
        textAlignment = TEXT_ALIGNMENT_GRAVITY
        
        gravity = Gravity.CENTER
        
        setColorOfBackground()
        
        attrs.recycle()
    
    }
    
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        
        when (event!!.action) {
            
            MotionEvent.ACTION_DOWN -> {
                setColorOfBackground(strokeColor = colorOfStroke, bgColor = rippleColor, buttonRippleAlpha = rippleAlpha)
                defaultElevation = elevation
                elevation = 25f
                invalidate()
            }
            
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                setColorOfBackground()
                elevation = defaultElevation
                invalidate()
            }
        }
        
        return super.onTouchEvent(event)
    }
    
    @JvmOverloads
    fun setColorOfBackground(
        strokeColor: String? = colorOfStroke,
        bgColor: String? = colorOfBackground,
        bgAlpha: Int = alphaOfBackground,
        strokeWidth: Int? = widthOfStroke,
        cornerRad: Float? = radiusOfCorners,
        buttonRippleColor : String? = null,
        buttonRippleAlpha : Int? = null
    ) {
        
        buttonRippleColor?.let {
            rippleColor = if (buttonRippleAlpha != null) {
                it.transparency(buttonRippleAlpha)
            } else {
                it
            }
        }
        
        background = GradientDrawable().apply {
            
            bgColor?.let { bg ->
                if (bgAlpha > -1) {
                    setColor(bg.toColor(bgAlpha))
                } else {
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

    fun activateErrorState() {
        setColorOfBackground(
            strokeColor = currentTheme.errorColor,
            bgColor = currentTheme.errorColor,
            bgAlpha = 60
        )
    }
    
    fun activateDisabledLook() {
        setColorOfBackground(
            bgAlpha = 50,
        )
    }
    
    fun resetToNormalLook() {
        setTextColor(colorOfText.toColor())
        setColorOfBackground()
    }
    
}