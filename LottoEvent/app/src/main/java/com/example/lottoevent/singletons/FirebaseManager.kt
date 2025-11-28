package com.example.lottoevent.singletons

import android.util.Log
import com.example.lottoevent.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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
                Log.i(TAG, "Successfully authenticated")
                val fetchRes = fetchUser(firebaseUser.uid)
                fetchRes
            }
            else {
                Log.e(TAG, "Successfully authenticated, but user is null")
                Result.failure(IllegalStateException("User is null after success"))
            }
        }
        catch (e: Exception) {
            Log.i(TAG, "Not authenticated")
            Result.failure(e)
        }
    }

    suspend fun addUser(user: User): Result<Unit> {
        return try {
            if (user.id.isEmpty()) {
                return Result.failure(IllegalArgumentException("User ID cannot be empty when adding to Firestore."))
            }
            db.collection("users")
                .document(user.id)
                .set(user, SetOptions.merge()) // Use merge to avoid overwriting events/waiting lists if they exist
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
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