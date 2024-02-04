package com.example.reserve_local.GestionAcces

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter


@Configuration
@EnableWebSecurity
class Securitéconfig {

    @Value("\${auth0.audience}")
    private val audience: String = String()

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private val issuer: String = String()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/", permitAll)
                authorize(HttpMethod.GET, "/**", permitAll)
                authorize(HttpMethod.PUT, "/Reservations/**", hasAuthority("PERMISSION_write"))
                authorize(HttpMethod.DELETE, "/Salles/**", hasAuthority("PERMISSION_delete"))
                authorize(HttpMethod.PUT, "/Salles/**", hasAuthority("PERMISSION_write"))
                authorize(HttpMethod.POST, "/Salles", hasAuthority("PERMISSION_create"))

               // authorize(HttpMethod.GET, "/Reservations", authenticated)
                //authorize(HttpMethod.POST, "/**", authenticated)
                //authorize(HttpMethod.PUT, "/**", authenticated)
                //authorize(HttpMethod.DELETE, "/**", authenticated)
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                jwt { }
            }
        }
        return http.build()
    }
    @Bean
     fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("permissions")
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("PERMISSION_")

        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter)

        jwtAuthenticationConverter.setPrincipalClaimName("sub")

        return jwtAuthenticationConverter
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer) as NimbusJwtDecoder
        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidateur(audience)
        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
        jwtDecoder.setJwtValidator(withAudience)
        return jwtDecoder
    }





}


