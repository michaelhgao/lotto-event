package com.example.lottoevent.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseManager {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val TAG = "FirebaseManager"
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun loginAnonymously(): Result<FirebaseUser> {
        return try {
            val authResult = auth.signInAnonymously().await()
            val user = authResult.user

            if (user != null) {
                Result.success(user)
            }
            else {
                Result.failure(IllegalStateException("User is null after success"))
            }
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

}