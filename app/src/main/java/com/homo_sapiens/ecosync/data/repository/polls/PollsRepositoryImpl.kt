package com.homo_sapiens.ecosync.data.repository.polls

import com.homo_sapiens.ecosync.BuildConfig
import com.homo_sapiens.ecosync.data.model.poll.PollDto
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.Settings
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class PollsRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    private var lastCreatedAt: Timestamp? = null
    suspend fun getPolls(getNext: Boolean, isRefreshing: Boolean = false) = flow {
        if (getNext.not()) return@flow
        // Refresh page
        if (isRefreshing) lastCreatedAt = null
        try {
            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)
            val data = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("polls")
                .collection("polls")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .whereLessThan("createdAt", lastCreatedAt ?: Timestamp.now())
                .get().await().toObjects(PollDto::class.java)
                .map { it.toPoll() }
            lastCreatedAt = data.last().createdAt
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

    suspend fun votePoll(
        pollId: String,
        vote: Int
    ) {
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("polls")
                .collection("polls")
                .document(pollId)
                .set(
                    hashMapOf(
                        "answers" to hashMapOf(
                            Settings.currentUser.userId to vote
                        )
                    ), SetOptions.merge()
                ).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}