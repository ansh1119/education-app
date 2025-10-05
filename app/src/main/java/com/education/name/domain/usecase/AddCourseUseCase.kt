package com.education.name.domain.usecase

import android.net.Uri
import com.education.name.data.repository.CourseRepository
import javax.inject.Inject

class AddCourseUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        imageUri: Uri?
    ): Result<Unit> {
        if (name.isBlank()) return Result.failure(Exception("Name cannot be empty"))
        return repository.addCourse(name, description, imageUri)
    }
}
