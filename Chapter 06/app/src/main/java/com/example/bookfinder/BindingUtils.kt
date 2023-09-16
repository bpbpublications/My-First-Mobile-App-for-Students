package com.example.bookfinder

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter



@BindingAdapter("visibility")
fun ProgressBar.setLoadingState(state: BookApiStatus?) {
    state?.let {
        visibility = when (state) {
            BookApiStatus.LOADING -> View.VISIBLE
            BookApiStatus.ERROR -> View.GONE
            BookApiStatus.DONE -> View.GONE
            BookApiStatus.EMPTY -> View.GONE
        }
    }
}


@BindingAdapter("list_authors")
fun TextView.setAuthors(authors: List<String>?) {
    authors?.let {
        text = authors.joinToString(", ")
    }
}

// show only first 100 characters
@BindingAdapter("describe")
fun TextView.setDescribe(describe: String?) {
    describe?.let {
        if (describe.length < 150) {
            text = describe
        } else {
            text = describe.substring(0, 150) + "..."
        }
    }
}