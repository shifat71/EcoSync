package com.homo_sapiens.ecosync.data.repository.profile

import com.homo_sapiens.ecosync.BuildConfig
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.data.model.auth.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.NullPointerException

class ProfileRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
) {

    suspend fun getUser(userId: String) = flow {
        try {
            emit(Resource.Loading())
            val user = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document(userId)
                .get()
                .await()
                .toObject(User::class.java)
            emit(Resource.Success(user ?: throw NullPointerException("Null User")))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }
}