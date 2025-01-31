package com.alanturing.nebula.model.authentication

data class  LoginResponse (
    val accessToken: String,
    val refreshToken: String
)