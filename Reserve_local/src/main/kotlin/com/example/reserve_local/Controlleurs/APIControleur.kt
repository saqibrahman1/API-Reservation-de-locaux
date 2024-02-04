package crosemont.tdi.g66.restaurantapirest.Controleurs

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class APIControleur {
    @GetMapping("/")
    fun index() = "Service de réservation"
    @GetMapping("/jeton")
    fun getPrincipalInfo(principal: JwtAuthenticationToken): Map<String, Any> {
        val authorities: Collection<String> = principal.authorities
                .stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .toList()

        val info: MutableMap<String, Any> = HashMap()
        info["name"] = principal.name
        info["authorities"] = authorities
        info["tokenAttributes"] = principal.tokenAttributes

        return info
    }


    @GetMapping("/utilisateur")
    fun lireUtilisateur(principal: Principal?): String? {
        if (principal != null) {
            return "Bonjour, " + principal.name
        } else {
            return null
        }
    }
    @GetMapping("/etudiant")
    @PreAuthorize("hasAuthority('ROLE_ETUDIANT')")
    fun getDataPourEtudiant(): ResponseEntity<Any> {
        val dataEtudiant = mapOf("message" to "Données réservées à l'étudiant")
        return ResponseEntity.ok(dataEtudiant)
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun getDataPourAdmin(): ResponseEntity<Any> {
        val dataAdmin = mapOf("message" to "Données réservées à l'administrateur")
        return ResponseEntity.ok(dataAdmin)
    }



}