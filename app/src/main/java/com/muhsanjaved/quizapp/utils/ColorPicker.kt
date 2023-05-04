package com.muhsanjaved.quizapp.utils

object ColorPicker {
    val colors = arrayOf(
        "#070A52",
        "#393646",
        "#576CBC",
        "#635985",
        "#2F58CD",
        "#0E8388",
        "#9E4784",
        "#D27685",
        "#E5E5CB",
        "#144272",
        "#5B8FB9",
        "#4E31AA",
        "#6D67E4",
    )

    var currentColorIndex = 0

    fun getColor():String {
        currentColorIndex =  (currentColorIndex + 1) % colors.size
        return colors[currentColorIndex]
    }
}