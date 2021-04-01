package info.firozansari.codingexercise.util

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun TextView?.setTextOrDefault(value: String?) {
    if (this == null) return
    if (value.isNullOrBlank()) {
        this.text = ""
        return
    }
    this.text = value
}

fun TextView?.setTextColorCompat(@ColorRes color: Int) {
    if (this == null) return
    this.setTextColor(ContextCompat.getColor(this.context, color))
}

