package com.example.reserve_local.Modele

import java.sql.Time


class Reservation(
        val CodeReserv: String,
        val HArrive: Time,
        val Hdepart: Time,
        val IdSalle: Int,
        val NbrPersonnes: Int,
        var IdUtilisateur: String?

    )
