package com.example.reserve_local.Services

import com.example.reserve_local.DAO.SalleDAO


import com.example.reserve_local.Modele.Reservation
import com.example.reserve_local.Modele.Salle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class SalleService (@Qualifier("salleDAOImpl") val dao: SalleDAO){
    fun chercherTous(): List<Salle> = dao.chercherTous()
    fun chercherParId(id:Int): Salle? = dao.chercherParId(id)
    fun ajouterSalle(salle: Salle): Salle {
        return dao.ajouterSalle(salle)
    }

    fun modifier(salle: Salle) = dao.modifier(salle)?.let { it }

    fun supprimer(salle: Salle): Salle? = dao.supprimer(salle)
    fun chercherParCode(code: String): Salle? = dao.chercherParCode(code)


}