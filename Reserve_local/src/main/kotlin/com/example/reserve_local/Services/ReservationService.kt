package com.example.reserve_local.Services

import com.example.reserve_local.DAO.ReservationDAO
import com.example.reserve_local.Modele.Reservation
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service


@Service
class ReservationService (@Qualifier("reservationDAOImpl") val dao: ReservationDAO) {
    fun chercherTous() : List<Reservation> = dao.chercherTous()
    fun chercherParCodeRéservation(CodeRéservation: String):Reservation? = dao.chercherParCodeRéservation(CodeRéservation)

    fun ajouter(reservation: Reservation): Reservation? = dao.ajouter(reservation)
    fun modifier(reservation: Reservation):Reservation?  = dao.modifier(reservation)

    fun supprimer(reservation: Reservation): Reservation? = dao.supprimer(reservation)

    fun getReservationsByUserId(userId: String): List<Reservation> {
        return dao.getReservationsByUserId(userId)
    }



}