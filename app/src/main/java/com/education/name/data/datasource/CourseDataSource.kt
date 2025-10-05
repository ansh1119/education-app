package com.education.name.data.datasource

import android.net.Uri
import com.education.name.domain.model.Course
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CourseDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    suspend fun uploadThumbnail(imageUri: Uri): String {
        val storageRef = storage.reference
            .child("course_thumbnails/${System.currentTimeMillis()}.jpg")

        storageRef.putFile(imageUri).await()
        return storageRef.downloadUrl.await().toString()
    }

    suspend fun addCourse(course: Course): Result<Unit> {
        return try {
            val newDoc = firestore.collection("courses").document()
            val courseWithId = course.copy(id = newDoc.id)
            newDoc.set(courseWithId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
