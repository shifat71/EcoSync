package com.homo_sapiens.ecosync.data.repository.events

import android.util.Log
import com.homo_sapiens.ecosync.BuildConfig
import com.homo_sapiens.ecosync.data.model.create.EventDto
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.Settings
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class EventsRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    private var lastCreatedAt: Timestamp? = null
    suspend fun getEvents(getNext: Boolean, isRefreshing: Boolean = false) = flow {

        if (getNext.not()) return@flow

        // Refresh page
        if (isRefreshing) lastCreatedAt = null
        try {

            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)

            val query = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("events")
                .collection("events")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get().await()

            val data = query.toObjects(EventDto::class.java)
                .map { it.toEvent() }

            lastCreatedAt = data.last().createdAt

            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }
}