package com.example.lottoevent.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseManager {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val TAG = "FirebaseManager"

    suspend fun addUser(user: User) {
        db.collection("users")
            .document(user.id)
            .set(user)
            .await()
    }
}