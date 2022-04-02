package com.landony.domain.entities

data class CommentsByPostResultUI(
    val userID: Long,
    val id: Long,
    val name: String,
    val email: String,
    val body: String
)
