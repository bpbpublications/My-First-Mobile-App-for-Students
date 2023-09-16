package com.example.inspireme

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("format_time")
fun formatTime(textView: TextView, time: Long) {
    val sdf = SimpleDateFormat("MMM dd,yy 'at' h:mm a", Locale.getDefault())
    val date = sdf.format(time)
    textView.text = date
}
