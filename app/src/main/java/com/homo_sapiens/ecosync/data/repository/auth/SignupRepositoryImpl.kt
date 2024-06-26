package com.homo_sapiens.ecosync.data.repository.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.homo_sapiens.ecosync.BuildConfig
import com.homo_sapiens.ecosync.data.model.auth.LoginResult
import com.homo_sapiens.ecosync.util.Settings
import com.homo_sapiens.ecosync.data.model.auth.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignupRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    suspend fun signupWithEmail(email: String, password: String, fullName: String): LoginResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            saveUserToDb(email, fullName, result.user?.uid)
            result.user?.sendEmailVerification()?.await()
            LoginResult(isSuccess = true)
        } catch (e: FirebaseAuthUserCollisionException) {
            LoginResult(emailError = "Email address is already in use", isSuccess = false)
        } catch (e: Exception) {
            e.printStackTrace()
            LoginResult(isSuccess = false)
        }
    }

    private suspend fun saveUserToDb(email: String, fullName: String, uid: String?) {
        if (uid.isNullOrEmpty()) throw FirebaseAuthInvalidCredentialsException("", "")
        val user = User(
            email = email,
            fullName = fullName,
            userId = uid
        )
        Settings.currentUser = user.toAppUser()
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document("$uid")
                .set(user)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}