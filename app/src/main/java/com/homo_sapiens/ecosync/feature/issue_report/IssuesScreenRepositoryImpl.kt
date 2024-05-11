package com.homo_sapiens.ecosync.feature.issue_report

import android.content.Context
import android.net.Uri
import com.homo_sapiens.ecosync.BuildConfig
import com.homo_sapiens.ecosync.util.Settings
import com.homo_sapiens.ecosync.data.model.share.PostDto
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.extension.fileFromContentUri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class IssuesScreenRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val storage: FirebaseStorage = Firebase.storage,
    private val context: Context
) {

    suspend fun submit(body: String, location: String, isAnonymous:Boolean, image: String, userId: String) = flow {
        emit(Resource.Loading())
        val userId = Settings.currentUser.userId
        val picPath =
            if (image.isNotEmpty()) uploadPostImage(image, userId) else null
        val issue = IssueDto(
            body = body,
            userId = userId,
            location = location,
            isAnonymous = isAnonymous,
            image = picPath ?: ""
        )

        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("issues")
                .collection("issues")
                .add(issue)
                .await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

    private suspend fun uploadPostImage(uri: String, userId: String): String? {
        return try {
            val compressedImageFile =
                Compressor.compress(context, Uri.parse(uri).fileFromContentUri(context))
            val file = Uri.fromFile(compressedImageFile)
            val storageRef = storage.reference
            val userPhotoRef = storageRef.child("images/$userId/${compressedImageFile.name}")
            userPhotoRef.putFile(file).await()
            userPhotoRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}