package com.example.reserve_local

import com.example.reserve_local.Controlleurs.salleControlleur
import com.example.reserve_local.DAO.SouceDonnées
import com.example.reserve_local.Modele.Salle
import com.example.reserve_local.Services.SalleService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class SalleControlleurTest {

    @Mock
    lateinit var salleService: SalleService

    @InjectMocks
    lateinit var salleControlleur: salleControlleur

    @Test
    fun `Étant donné obtenirSalle retourne la liste of salles`() {

        val expectedSalles = SouceDonnées.Salle
        whenever(salleService.chercherTous()).thenReturn(expectedSalles)


        val result = salleControlleur.obtenirSalle()


        assertEquals(expectedSalles, result)
    }

    @Test
    fun `Étant donné obtenirSalleParId retourne salle pour être valider par id`() {

        val id = 5
        val expectedSalle = SouceDonnées.Salle.find { it.idSalle== id }
        whenever(salleService.chercherParId(id)).thenReturn(expectedSalle)


        val response = salleControlleur.obtenirSalleParId(id)


        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedSalle, response.body)
    }

    @Test
    fun `Étant donné obtenirSalleParId retourne pas trouver pour cet id`() {

        val id = 99
        whenever(salleService.chercherParId(id)).thenReturn(null)


        val response = salleControlleur.obtenirSalleParId(id)


        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `Étant donné inscrireSalle renvoie la salle lorsque l'ajout est réussi`() {

        val nouvelleSalle = Salle(3, "B207", "Windows", "10:00", "19:00", "salle3", 3)
        `when`(salleService.ajouter(nouvelleSalle)).thenReturn(nouvelleSalle)

        val réponse = salleControlleur.inscrireSalle(nouvelleSalle)

        assertEquals(HttpStatus.OK, réponse.statusCode)
        assertEquals(nouvelleSalle, réponse.body)
    }

    @Test
    fun `Étant donné inscrireSalle renvoie une erreur serveur interne lorsque l'ajout échoue`() {

        val nouvelleSalle = Salle(3, "B207", "Windows", "10:00", "19:00", "salle3", 3)
        `when`(salleService.ajouter(nouvelleSalle)).thenReturn(null)

        val réponse = salleControlleur.inscrireSalle(nouvelleSalle)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, réponse.statusCode)
    }

    @Test
    fun `Étant donné modifierSalle renvoie la salle mise à jour`() {

        val salleMiseAJour = Salle(1, "B205", "Windows", "10:00", "20:00", "salle2", 1)
        `when`(salleService.modifier(salleMiseAJour)).thenReturn(salleMiseAJour)

        val réponse = salleControlleur.modifierSalle(salleMiseAJour)

        assertEquals(HttpStatus.OK, réponse.statusCode)
        assertEquals(salleMiseAJour, réponse.body)
    }

    @Test
    fun `Étant donné modifierSalle renvoie non trouvé pour une salle inexistante`() {

        val salleInexistante = Salle(99, "Inexistante", "None", "00:00", "00:00", "none", 0)
        `when`(salleService.modifier(salleInexistante)).thenReturn(null)

        val réponse = salleControlleur.modifierSalle(salleInexistante)

        assertEquals(HttpStatus.NOT_FOUND, réponse.statusCode)
    }

    @Test
    fun `Étant donné supprimerSalle renvoie OK pour une salle existante`() {

        val id = 1
        val salleExistante = Salle(id, "B205", "Windows", "10:00", "19:00", "salle2", 1)
        `when`(salleService.chercherParId(id)).thenReturn(salleExistante)

        val réponse = salleControlleur.supprimerSalle(id)

        assertEquals(HttpStatus.OK, réponse.statusCode)
    }

    @Test
    fun `Étant donné supprimerSalle renvoie non trouvé pour une salle inexistante`() {

        val id = 99
        `when`(salleService.chercherParId(id)).thenReturn(null)
        val réponse = salleControlleur.supprimerSalle(id)
        assertEquals(HttpStatus.NOT_FOUND, réponse.statusCode)
    }




}

