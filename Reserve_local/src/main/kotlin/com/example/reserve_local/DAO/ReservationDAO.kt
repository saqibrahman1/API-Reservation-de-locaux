package com.example.reserve_local.DAO

import com.example.reserve_local.Modele.Reservation
import org.springframework.stereotype.Repository

@Repository
interface ReservationDAO : DAO<Reservation> {
    override fun chercherTous(): List<Reservation>
    fun chercherParCodeRéservation(CodeRéservation: String): Reservation?

    fun getReservationsByUserId(userId: String): List<Reservation>


}


