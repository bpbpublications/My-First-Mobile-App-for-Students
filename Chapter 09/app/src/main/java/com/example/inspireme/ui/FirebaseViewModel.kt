package com.example.inspireme.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inspireme.models.FirePost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class CloudUploadStatus { SUCCESS, FAILURE, IN_PROGRESS, NONE }
enum class PostLoadingStatus { SUCCESS, FAILURE, IN_PROGRESS, NONE }
class FirebaseViewModel(
    private val auth: FirebaseAuth, private val db: FirebaseFirestore
) : ViewModel() {

    private val _cloudUploadStatus = MutableLiveData(CloudUploadStatus.NONE)
    val postUploadStatus get() = _cloudUploadStatus

    private val _postLoadingStatus = MutableLiveData(PostLoadingStatus.NONE)
    val postLoadingStatus get() = _postLoadingStatus

    private val _authState = MutableLiveData(AuthState.UNAUTHENTICATED)
    val authState get() = _authState

    private val _response = MutableLiveData<String>()
    val response get() = _response

    private val _firePostList = MutableLiveData<List<FirePost>>()
    val posts get() = _firePostList

    private fun loggedInUser() = auth.currentUser

    fun getAllFirePosts() = db.collection("posts").get().addOnFailureListener {
        queryFailure(it.message)
    }.addOnSuccessListener {
        _firePostList.value = it.toObjects(FirePost::class.java)
        _postLoadingStatus.value = PostLoadingStatus.SUCCESS
        _response.value = "Successfully loaded posts ✅"
    }

    private fun saveFirePost(firePost: FirePost) {
        _cloudUploadStatus.value = CloudUploadStatus.IN_PROGRESS
        db.collection("posts").add(firePost).addOnFailureListener {
            queryFailure(it.message)
        }.addOnSuccessListener {
            _cloudUploadStatus.value = CloudUploadStatus.SUCCESS
            _response.value = "Successfully added post ✅"
        }
    }

    private fun queryFailure(message: String?) {
        _postLoadingStatus.value = PostLoadingStatus.FAILURE
        _cloudUploadStatus.value = CloudUploadStatus.FAILURE
        _response.value = message.toString()
    }

    // methods for fragment to call
    fun addNewPost(subject: String, content: String) {
        if (subject.isNotEmpty() && content.isNotEmpty() && loggedInUser() != null) {
            saveFirePost(
                FirePost(
                    subject,
                    content,
                    timestamp = System.currentTimeMillis(),
                    name = loggedInUser()?.displayName!!,
                    userId = loggedInUser()?.uid!!
                )
            )
        } else {
            queryFailure("Please enter valid title and description")
        }
    }

    fun updateUserAuthState() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.AUTHENTICATED
        }
    }
}

class FirebaseViewModelFactory(
    private val auth: FirebaseAuth, private val db: FirebaseFirestore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirebaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return FirebaseViewModel(auth, db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}