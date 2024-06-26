package com.homo_sapiens.ecosync.data.repository.profile

import com.homo_sapiens.ecosync.BuildConfig
import com.homo_sapiens.ecosync.data.model.create.EventDto
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.Settings
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProfileAttendedEventsRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    suspend fun getEvents(getNext: Boolean, userId: String) = flow {
        if (getNext.not()) return@flow
        try {
            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)
            val data = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("events")
                .collection("events")
                .whereArrayContains("attendee", userId)
                .get().await().toObjects(EventDto::class.java)
                .map { it.toEvent() }
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }
}