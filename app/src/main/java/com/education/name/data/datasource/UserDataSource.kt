package com.education.name.data.datasource

import android.util.Log
import com.education.name.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    fun createUser(user: User) {
        val uid = auth.currentUser?.uid ?: user.email
        firestore.collection("users")
            .document(user.email)
            .set(user)
            .addOnSuccessListener { Log.d("USER DATASOURCE","user document created") }
            .addOnFailureListener { e -> Log.d("USER DATASOURCE","user document creation fail:- "+e.message) }
    }
}
