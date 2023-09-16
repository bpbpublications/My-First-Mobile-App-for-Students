package com.example.inspireme.models

// default values are required for Firebase models or else the app will crash
data class FirePost(
    val title: String = "", // title
    val content: String = "", // body
    val timestamp: Long = 0L, // date and time
    val name: String = "",    // author
    val userId: String = "",  // unique id from Firebase
)