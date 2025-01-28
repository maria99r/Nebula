package com.alanturing.nebula.model.authentication

data class AuthRequest(
    val email: String,
    val password: String,
)