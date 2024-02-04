package com.example.reserve_local.DAO

import com.example.reserve_local.Modele.Salle
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class SalleDAOImpl(val db: JdbcTemplate, private val jdbcTemplate: JdbcTemplate) : SalleDAO{
    override fun chercherTous(): List<Salle> {
        val sql = "SELECT * FROM Salle"
        return db.query(sql) { rs, _ ->
            Salle(
                idSalle = rs.getInt("idSalle"),
                nombrePersonnes = rs.getInt("nombrePersonnes"),
                heureOuverture = rs.getString("heureOuverture"),
                heureFermeture = rs.getString("heureFermeture"),
                nomSalle = rs.getString("nomSalle"),
                imageSalle = rs.getString("imageSalle"),
                equipement = rs.getString("Equipement")

            )

            /*
   val idSalle: Int,
        val nombrePersonnes: Int,
        val nomSalle: String,
        val heureOuverture: String,
        val heureFermeture: String,
        val Equipement: String,
        val imageSalle: String
)

             */
        }
    }

    override fun chercherParId(id: Int): Salle? {
        val sql = "SELECT * FROM Salle WHERE idSalle = ?"
        return db.queryForObject(sql, { rs, _ ->
            Salle(
                idSalle = rs.getInt("idSalle"),
                nombrePersonnes = rs.getInt("nombrePersonnes"),
                heureOuverture = rs.getString("heureOuverture"),
                heureFermeture = rs.getString("heureFermeture"),
                nomSalle = rs.getString("nomSalle"),
                imageSalle = rs.getString("imageSalle"),
                equipement = rs.getString("Equipement")
            )
        }, id)
    }

    override fun chercherParCode(code: String):  Salle? {
        TODO("Not yet implemented")
    }

    override fun ajouter(element: Salle): Salle? {
        TODO("Not yet implemented")
    }

    override fun ajouterSalle(salle: Salle): Salle {
        val sql = "INSERT INTO Salle (IdSalle, nombrePersonnes, heureOuverture, heureFermeture, nomSalle, imageSalle, Equipement) VALUES (?, ?, ?, ?, ?, ?, ?)"
        jdbcTemplate.update(sql, salle.idSalle, salle.nombrePersonnes, salle.heureOuverture, salle.heureFermeture, salle.nomSalle, salle.imageSalle, salle.equipement)
        return salle
    }


    override fun modifier(salle: Salle): Salle? {
        val sql = """
        UPDATE Salle 
        SET nombrePersonnes = ?, 
            nomSalle = ?, 
            heureOuverture = ?, 
            heureFermeture = ?, 
            Equipement = ?, 
            imageSalle = ?
        WHERE IdSalle = ?
    """
        val updated = db.update(sql,
                salle.nombrePersonnes,
                salle.nomSalle,
                salle.heureOuverture,
                salle.heureFermeture,
                salle.equipement,
                salle.imageSalle,
                salle.idSalle)

        return if (updated > 0) salle else null
    }

    override fun supprimer(salle: Salle): Salle? {
        // Supprimez d'abord les réservations liées
        val sqlDeleteReservations = "DELETE FROM Reservation WHERE IdSalle = ?"
        db.update(sqlDeleteReservations, salle.idSalle)

        // Ensuite, supprimez la salle
        val sqlDeleteSalle = "DELETE FROM Salle WHERE IdSalle = ?"
        val updated = db.update(sqlDeleteSalle, salle.idSalle)
        return if (updated > 0) salle else null
    }





}