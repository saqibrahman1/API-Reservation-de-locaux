package com.example.reserve_local.DAO

import com.example.reserve_local.Modele.Reservation
import com.example.reserve_local.Modele.Salle
import org.springframework.stereotype.Component
import java.sql.Time

@Component
class SouceDonnées {
    companion object{

        val Reservations = mutableListOf(

            Reservation("ES100", Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), 1, 15, "656d0dba34408e731c39de12"),
            Reservation ("RES100", Time.valueOf("10:00:00"), Time.valueOf("15:00:00"), 6, 3, "auth0|656d0dba34408e731c39de12"),
            Reservation  ("RES101", Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 5, 2, "auth0|656d0dba34408e731c39de12"),

            )




/*
        val Salles = mutableListOf(
               // Salle(1, 4, Time.valueOf("09:00:00"), Time.valueOf("11:00:00"), 1)
        )

 */
        val Salle = mutableListOf(

        Salle (5,4,"8:00","10:00","B205","salle2","Windows"),
        Salle (6,4,"8:00","10:00","B305","salle2","Windows")

        )



    }
}