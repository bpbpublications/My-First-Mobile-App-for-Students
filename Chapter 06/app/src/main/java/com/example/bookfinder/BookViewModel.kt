package com.example.bookfinder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinder.network.Book
import com.example.bookfinder.network.BookFinderApi
import kotlinx.coroutines.launch


enum class BookApiStatus { LOADING, ERROR, DONE, EMPTY }
class BookViewModel() : ViewModel() {

    private val _status = MutableLiveData<BookApiStatus>(BookApiStatus.EMPTY)
    val status: LiveData<BookApiStatus> = _status

    private var _query = MutableLiveData<String>("")
    val query: LiveData<String> = _query

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    fun setQuery(query: String) {
        _query.value = query
    }

    private fun getBooks() {
        _status.value = BookApiStatus.LOADING
        viewModelScope.launch {
            try {
                val response = BookFinderApi.retrofitService.getDetails(
                    searchString = _query.value!!,
                    maxResults = "10",
                    printType = "books"
                )
                _response.value = "10 out of ${response.totalItems} books found."
                _books.value = response.items
                _status.value = BookApiStatus.DONE
                Log.d("BookViewModel", "getBooks: ${_books.value}")
            } catch (e: Exception) {
                _status.value = BookApiStatus.ERROR
                _books.value = ArrayList()
                _response.value = "Error: ${e.message}"
                Log.d("BookViewModel", "getBooks: ${e.message}")
            }
        }
    }

    fun searchBook() {
        if (_query.value != "") {
            getBooks()

        }
    }

    fun resetStatus() {
        _status.value = BookApiStatus.EMPTY
    }
}