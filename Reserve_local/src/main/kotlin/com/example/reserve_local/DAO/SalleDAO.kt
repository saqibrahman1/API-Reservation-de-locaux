package com.example.reserve_local.DAO

import com.example.reserve_local.Modele.Salle
import org.springframework.stereotype.Repository

@Repository
interface SalleDAO : DAO<Salle> {
    override fun chercherTous(): List<Salle>
    fun chercherParId(id: Int): Salle?
    fun ajouterSalle(salle: Salle): Salle


}