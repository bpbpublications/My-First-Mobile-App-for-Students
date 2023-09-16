package com.example.inspireme.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

enum class AuthState {
    AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
}

class CloudAuthViewModel(private val auth: FirebaseAuth) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.UNAUTHENTICATED)
    val authState get() = _authState

    private val _authMsg = MutableLiveData<String>()
    val authMsg get() = _authMsg

    private fun firebaseSignIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
            invalidAuth(it.message)
        }.addOnSuccessListener {
            _authState.value = AuthState.AUTHENTICATED
        }

    private fun firebaseSignUp(username: String, email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            _authState.value = AuthState.AUTHENTICATED
            // update user profile with username
            it.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(username).build()
            )
        }.addOnFailureListener {
            invalidAuth(it.message)
        }

    private fun invalidAuth(message: String?) {
        _authState.value = AuthState.INVALID_AUTHENTICATION
        _authMsg.value = message.toString()
    }

    // methods for fragment to call

    fun performLogin(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseSignIn(email, password)
        } else {
            invalidAuth("Please enter valid email and password")
        }
    }

    fun performRegistration(username: String, email: String, password: String) {
        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            firebaseSignUp(username, email, password)
        } else {
            invalidAuth("Please enter valid username, email and password")
        }
    }

    fun updateUserAuthState() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.AUTHENTICATED
        }
    }
}

class CloudAuthViewModelFactory(auth: FirebaseAuth) :
    ViewModelProvider.Factory {
    private val auth = auth
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CloudAuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CloudAuthViewModel(auth) as T
        }
        throw IllegalArgumentException("Unable to construct cloud auth viewmodel")
    }
}