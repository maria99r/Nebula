package com.alanturing.nebula.model.authentication

data class SignUpResponse (
    val email: String,
    val password: String,
    val role : String
)