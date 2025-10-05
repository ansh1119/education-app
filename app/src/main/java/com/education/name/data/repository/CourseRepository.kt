package com.education.name.data.repository

import android.net.Uri
import androidx.core.net.toUri
import com.education.name.data.datasource.CourseDataSource
import com.education.name.domain.model.Course
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val dataSource: CourseDataSource
) {

    suspend fun addCourse(
        name: String,
        description: String,
        imageUri: Uri?
    ): Result<Unit> {
        return try {
            val thumbnailUrl = imageUri?.let { dataSource.uploadThumbnail(it) } ?: ""
            val course = Course(
                name = name,
                description = description,
                thumbnail = thumbnailUrl.toUri()
            )
            dataSource.addCourse(course)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
