package com.homo_sapiens.ecosync.util

import com.homo_sapiens.ecosync.BuildConfig
import com.homo_sapiens.ecosync.data.model.auth.AppUser
import com.homo_sapiens.ecosync.data.model.auth.PostUser
import com.homo_sapiens.ecosync.data.model.auth.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


object Settings {
    var currentUser: AppUser = AppUser()
        set(value) {
            Firebase.crashlytics.setUserId(field.userId)
            field = value
        }
        get() {
            if (field.userId.isEmpty()) {
                field = field.copy(
                    userId = Firebase.auth.currentUser?.uid
                        ?: throw NullPointerException("Settings user must not be null")
                )
            }
            return field
        }

    var userStorage: List<PostUser> = emptyList()

    suspend fun getUserRole(userId: String): String? {
        val doc = Firebase.firestore.collection("users").document(userId).get().await()
        return doc.getString("role")
    }

    suspend fun getAllUsers(db: FirebaseFirestore) {
        try {
            userStorage = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .get().await()
                .toObjects(User::class.java)
                .map { it.toPostUser() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}