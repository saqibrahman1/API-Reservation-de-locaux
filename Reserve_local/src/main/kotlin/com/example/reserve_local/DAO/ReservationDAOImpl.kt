package com.example.reserve_local.DAO

import com.example.reserve_local.Modele.Reservation
import org.springframework.jdbc.core.BeanPropertyRowMapper


import org.springframework.jdbc.core.JdbcTemplate

import org.springframework.stereotype.Repository

@Repository
class ReservationDAOImpl(val db: JdbcTemplate) : ReservationDAO {
    override fun chercherTous(): List<Reservation> {
        val sql = "SELECT * FROM Reservation"
        return db.query(sql) { rs, _ ->
            Reservation(
                    CodeReserv = rs.getString("CodeReserv"),
                    HArrive = rs.getTime("HArrive"),
                    Hdepart = rs.getTime("Hdepart"),
                    IdSalle = rs.getInt("IdSalle"),
                    NbrPersonnes = rs.getInt("NbrPersonnes"),
                    IdUtilisateur = rs.getString("IdUtilisateur")
            )
        }
    }



    override fun chercherParCodeRéservation(CodeRéservation: String): Reservation? {
        val sql = "SELECT * FROM Reservation WHERE CodeReserv = ?"
        return db.queryForObject(sql, { rs, _ ->
            Reservation(
                    CodeReserv = rs.getString("CodeReserv"),
                    HArrive = rs.getTime("HArrive"),
                    Hdepart = rs.getTime("Hdepart"),
                    IdSalle = rs.getInt("IdSalle"),
                    NbrPersonnes = rs.getInt("NbrPersonnes"),
                    IdUtilisateur = rs.getString("IdUtilisateur")
            )
        }, CodeRéservation)
    }

    override fun getReservationsByUserId(userId: String): List<Reservation> {
        val sql = "SELECT * FROM Reservation WHERE IdUtilisateur = ?"
        return db.query(sql, { rs, _ ->
            Reservation(
                    CodeReserv = rs.getString("CodeReserv"),
                    HArrive = rs.getTime("HArrive"),
                    Hdepart = rs.getTime("Hdepart"),
                    IdSalle = rs.getInt("IdSalle"),
                    NbrPersonnes = rs.getInt("NbrPersonnes"),
                    IdUtilisateur = rs.getString("IdUtilisateur")
            )
        }, userId)
    }


    override fun chercherParCode(code: String): Reservation? = SouceDonnées.Reservations.find { it.CodeReserv==code }


    override fun ajouter(reservation: Reservation): Reservation? {
        val sql = "INSERT INTO Reservation (CodeReserv, HArrive, Hdepart, IdSalle, NbrPersonnes, IdUtilisateur) VALUES (?, ?, ?, ?, ?, ?)"
        db.update(sql, reservation.CodeReserv, reservation.HArrive, reservation.Hdepart, reservation.IdSalle, reservation.NbrPersonnes, reservation.IdUtilisateur)
        return reservation
    }


    override fun modifier(reservation: Reservation): Reservation? {
        val sql = "UPDATE Reservation SET HArrive = ?, Hdepart = ?, IdSalle = ?, NbrPersonnes = ?, IdUtilisateur = ? WHERE CodeReserv = ?"
        val updated = db.update(sql, reservation.HArrive, reservation.Hdepart, reservation.IdSalle, reservation.NbrPersonnes, reservation.IdUtilisateur, reservation.CodeReserv)

        return if (updated > 0) reservation else null
    }
    override fun supprimer(reservation: Reservation): Reservation? {
        val sql = "DELETE FROM Reservation WHERE CodeReserv = ?"
        val deleted = db.update(sql, reservation.CodeReserv)

        return if (deleted > 0) reservation else null
    }





}