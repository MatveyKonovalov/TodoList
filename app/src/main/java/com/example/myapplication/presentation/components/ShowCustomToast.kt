package com.example.myapplication.presentation.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

// Вывод всплывающего окна, говорящего об ошибке
fun showCustomToast(
    context: Context,
    message: String,
    backgroundColor: Int,
    textColor: Int
) {
    val toast = Toast(context)

    val textView = TextView(context).apply {
        text = message
        setTextColor(textColor)
        setPadding(48, 24, 48, 24)
        textSize = 14f
        setBackgroundColor(backgroundColor)

        // Создание закругления
        val drawable = GradientDrawable().apply {
            setColor(backgroundColor)
            cornerRadius = 16f
        }
        background = drawable
    }

    toast.view = textView
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(Gravity.BOTTOM, 0, 100)
    toast.show()
}