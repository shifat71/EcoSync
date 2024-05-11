package com.homo_sapiens.ecosync.data.repository.auth

import com.homo_sapiens.ecosync.BuildConfig
import com.homo_sapiens.ecosync.util.Screen
import com.homo_sapiens.ecosync.util.Settings
import com.homo_sapiens.ecosync.data.event.auth.SplashEvent
import com.homo_sapiens.ecosync.data.model.auth.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SplashRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
) {

    suspend fun checkUser(): SplashEvent {
        return try {
            if (auth.currentUser == null) SplashEvent.OnNavigate(Screen.Intro.route)
            else {
                auth.currentUser?.reload()?.await()
                if (auth.currentUser?.isEmailVerified == false) {
                    SplashEvent.ShowVerifyPopup
                } else {
                    getUser()
                    SplashEvent.OnNavigate(Screen.Home.route)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            SplashEvent.OnNavigate(Screen.Intro.route)
        }
    }

    private suspend fun getUser() {
        try {
            val user = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document("${auth.currentUser?.uid}")
                .get()
                .await()
                .toObject(User::class.java)
            Settings.currentUser = user?.toAppUser() ?: return
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}