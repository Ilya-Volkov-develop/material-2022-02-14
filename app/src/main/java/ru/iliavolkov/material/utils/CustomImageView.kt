package ru.iliavolkov.material.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CustomImageView @JvmOverloads constructor(
    context:Context,
    attributeSet:AttributeSet?=null,
    defStyleAtr:Int=0
):AppCompatImageView(context,attributeSet,defStyleAtr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}