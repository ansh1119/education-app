package com.education.name.data.repository

import com.education.name.data.datasource.AuthDataSource
import com.education.name.domain.model.User
import com.education.name.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Result

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signUp(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("User creation failed"))

            val user = User(
                name = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: "",
                role = "STUDENT"
            )

            // Store user in Firestore
            firestore.collection("users").document(firebaseUser.uid).set(user).await()

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("User not found"))

            // Fetch user data from Firestore
            val snapshot = firestore.collection("users").document(firebaseUser.uid).get().await()
            val user = snapshot.toObject(User::class.java) ?: return Result.failure(Exception("User data not found"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
