package com.education.name.domain.usecase


import com.education.name.data.repository.UserRepository
import com.education.name.domain.model.User
import com.education.name.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import javax.inject.Inject
import kotlin.Result

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email and password cannot be empty"))
        }

        return repository.signUp(email, password)
    }
}


class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email and password cannot be empty"))
        }

        return try {
            val authResult = repository.signIn(email, password)

            val user = userRepository.getUserById(email)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
