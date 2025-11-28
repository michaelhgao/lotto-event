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

    suspend fun loginAnonymously(): Result<User> {
        return try {
            val authResult = auth.signInAnonymously().await()
            val firebaseUser = authResult.user

            if (firebaseUser != null) {
                val fetchRes = fetchUser(firebaseUser.uid)
                fetchRes
            }
            else {
                Result.failure(IllegalStateException("User is null after success"))
            }
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchUser(uid: String): Result<User> {
        return try {
            val snapshot = db.collection("users")
                .document(uid)
                .get()
                .await()

            val user = snapshot.toObject(User::class.java)

            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(NoSuchElementException("User profile not found in Firestore for UID: $uid"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}