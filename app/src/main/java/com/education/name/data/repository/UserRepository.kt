package com.education.name.data.repository

import com.education.name.data.datasource.UserDataSource
import com.education.name.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource,
    private val firestore: FirebaseFirestore
) {
    fun createUser(user: User) {
        userDataSource.createUser(user)
    }

    suspend fun getUserById(email: String): User {
        val snapshot = firestore.collection("users").document(email).get().await()
        return snapshot.toObject(User::class.java)
            ?: throw Exception("User not found")
    }
}
