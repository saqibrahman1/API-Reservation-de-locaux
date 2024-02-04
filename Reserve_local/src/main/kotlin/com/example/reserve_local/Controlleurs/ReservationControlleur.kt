package com.example.reserve_local.Controlleurs



import com.example.reserve_local.Modele.Reservation
import com.example.reserve_local.Services.ReservationService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt




@RestController
@RequestMapping("\${api.base-path:}")
@Tag(
    name = "Reservation",
    description = "Points d'accès aux ressources liées aux reservations inscrits au service."
)

class ReservationControlleur (val service: ReservationService){
    @Operation(summary = "Obtenir la liste des réservations de l'utilisateur actuel")
    @GetMapping("/Reservations")
    fun getReservationsForCurrentUser(@AuthenticationPrincipal jwt: Jwt): ResponseEntity<*> {
        val userIdString = jwt.getClaimAsString("sub")
        val admin = jwt.getClaimAsString("permissions")

        if (userIdString != null) {
            val userIdInt = userIdString.toString()
            val reservations = if (admin?.contains("administrator") == true) {

                service.chercherTous()
            } else {

                service.getReservationsByUserId(userIdInt)
            }
            return ResponseEntity.ok(reservations)
        } else {
            val errorMessage = "ID de l'utilisateur invalide: $userIdString"
            val errorResponse = mapOf("error" to errorMessage)
            return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
        }
    }




    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Reservation trouvé"),
        ApiResponse(responseCode = "404", description = "Reservation inexistant")
    ])

    @GetMapping("/Reservations/{Code_reservation}")
    fun obtenirReservationParCode(@PathVariable Code_reservation: String, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<Reservation> {
        val userIdString = jwt.getClaimAsString("sub")
        val admin = jwt.getClaimAsString("permissions")

        if (userIdString != null) {
            val reservation = service.chercherParCodeRéservation(Code_reservation)

            if (reservation != null) {

                if (admin?.contains("administrator") == true || reservation.IdUtilisateur == userIdString) {
                    return ResponseEntity.ok(reservation)
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                }
            } else {
                return ResponseEntity.badRequest().build()
            }
        } else {
            return ResponseEntity.notFound().build()
        }
    }





    @PostMapping("/Reservations")
    fun inscrireReservation(@RequestBody reservation: Reservation, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<Reservation> {
        val userIdString = jwt.getClaimAsString("sub")
        val admin = jwt.getClaimAsString("permissions")

        if (admin?.contains("administrator") == true) {
            val added = service.ajouter(reservation)
            return ResponseEntity.ok(added)
        } else {

            reservation.IdUtilisateur = userIdString
            val added = service.ajouter(reservation)
            return ResponseEntity.ok(added)
        }
    }



    @PutMapping("/Reservations/{Code_reservation}")
    fun modifierReservation(@PathVariable Code_reservation: String, @RequestBody reservation: Reservation): ResponseEntity<Reservation> {
        if (reservation.CodeReserv != Code_reservation) {
            return ResponseEntity.badRequest().build()
        }
        val updated = service.modifier(reservation)
        return if (updated != null) ResponseEntity.ok(updated) else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/Reservations/{CodeReserv}")
    fun supprimerReservation(@PathVariable CodeReserv: String, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<Any> {
        val userIdString = jwt.getClaimAsString("sub")


        val permissions = jwt.getClaimAsString("permissions")
        val reservation = service.chercherParCodeRéservation(CodeReserv)

        if (reservation != null) {
            if (permissions?.contains("delete") == true || reservation.IdUtilisateur == userIdString) {
                service.supprimer(reservation)
                val Message = "La reservation a ete supprimer avec succees"
                val Response = mapOf("Confirmation:" to Message)
                return ResponseEntity(Response,HttpStatus.OK)
            } else {
                val errorMessage = "Cette réservation n'appartient pas à vous"
                val errorResponse = mapOf("error" to errorMessage)
                return ResponseEntity(errorResponse,HttpStatus.UNAUTHORIZED)
            }
        }

        return ResponseEntity(HttpStatus.NOT_FOUND)

    }



}





