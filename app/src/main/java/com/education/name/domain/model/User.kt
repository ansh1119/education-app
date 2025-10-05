package com.education.name.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String="", val email: String= "", val role: String="")
