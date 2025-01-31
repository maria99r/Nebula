package com.alanturing.nebula.model.authentication

data class SignUpResponse (
    val userId: Int,
    val email: String,
    val role : String
)

