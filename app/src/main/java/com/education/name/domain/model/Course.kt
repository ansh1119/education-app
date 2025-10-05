package com.education.name.domain.model

import android.net.Uri

data class Course(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val thumbnail: Uri? = null,
    val videos: List<String> = listOf<String>(),
    val createdAt: Long = System.currentTimeMillis()
)
