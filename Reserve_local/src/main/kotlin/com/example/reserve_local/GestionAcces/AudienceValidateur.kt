package com.example.reserve_local.GestionAcces

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt


class AudienceValidateur(private val audience: String) : OAuth2TokenValidator<Jwt> {

    override fun validate(jwt: Jwt): OAuth2TokenValidatorResult {
        val error = OAuth2Error("invalide_jeton", "Il manque l'audience requise", null)
        return if (jwt.audience.contains(audience)) {
            OAuth2TokenValidatorResult.success()
        } else OAuth2TokenValidatorResult.failure(error)
    }

}