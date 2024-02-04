package com.example.reserve_local.Controlleurs



import com.example.reserve_local.Modele.Reservation
import com.example.reserve_local.Modele.Salle
import com.example.reserve_local.Services.SalleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class salleControlleur (val service : SalleService){


    @GetMapping("/Salles")
    fun obtenirSalle() = service.chercherTous()

    @GetMapping("/Salles/{id_salle}")
    fun obtenirSalleParId(@PathVariable id_salle: Int): ResponseEntity<Salle> =
            service.chercherParId(id_salle)?.let { ResponseEntity.ok(it) }
                    ?: ResponseEntity(HttpStatus.NOT_FOUND)

    @PostMapping("/Salles")
    fun ajouterSalle(@RequestBody salle: Salle): ResponseEntity<Salle> {
        val nouvelleSalle = service.ajouterSalle(salle)
        return ResponseEntity(nouvelleSalle, HttpStatus.CREATED)
    }

    @PutMapping("/Salles/{id_salle}")
    fun modifierSalle(@PathVariable id_salle: Int, @RequestBody salle: Salle): ResponseEntity<Salle> {
        if (salle.idSalle != id_salle) {
            return ResponseEntity.badRequest().build() // ID incohérent
        }
        return service.modifier(salle)?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }


    @DeleteMapping("/Salles/{id_salle}")
    fun supprimerSalle(@PathVariable id_salle: Int): ResponseEntity<Any> =
            service.chercherParId(id_salle)?.let {
                service.supprimer(it)
                ResponseEntity(HttpStatus.OK)
            } ?: ResponseEntity(HttpStatus.NOT_FOUND)
}






