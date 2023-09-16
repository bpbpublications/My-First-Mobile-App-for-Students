package com.example.flashcards

import android.app.Application
import com.example.flashcards.data.NoteDatabase

class MyApp : Application() {
    val database: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }
}