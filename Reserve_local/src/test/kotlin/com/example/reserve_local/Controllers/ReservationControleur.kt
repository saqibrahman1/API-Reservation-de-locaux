package crosemont.tdi.g66.restaurantapirest.Controleurs

import com.example.reserve_local.Controlleurs.ReservationControlleur
import com.example.reserve_local.Controlleurs.salleControlleur
import com.example.reserve_local.DAO.SouceDonnées
import com.example.reserve_local.Modele.Reservation
import com.example.reserve_local.Services.ReservationService
import com.example.reserve_local.Services.SalleService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nimbusds.jwt.JWT
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.junit.jupiter.api.Assertions.assertEquals
import java.sql.Time
import org.mockito.Mockito.`when`
import org.springframework.http.ResponseEntity

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class ReservationControleur {

    @Mock
    lateinit var ReservationService: ReservationService

    @InjectMocks
    lateinit var ReservationControlleur: ReservationControlleur


    @Test
    fun `Étant donné obtenirReservationParCode pour une réservation existante`() {

        val reservationCode = "ES100"
        val expectedReservation = SouceDonnées.Reservations.find { it.CodeReserv == reservationCode }
        `when`(ReservationService.chercherParCodeRéservation(reservationCode)).thenReturn(expectedReservation)

        val response: ResponseEntity<Reservation> =
            ReservationControlleur.obtenirReservationParCode(reservationCode, JWT())

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedReservation, response.body)
    }

    @Test
    fun `Étant donné obtenirReservationParCode pour une réservation non trouvée`() {
        val id = "ASD123"
        whenever(ReservationService.chercherParCodeRéservation(id)).thenReturn(null)


        val response = ReservationControlleur.obtenirReservationParCode(id, JWT())


        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `Test inscrireReservation avec succès`() {
        val nouvelleReservation = Reservation(
            "ASD123",
            Time.valueOf("09:00:00"),
            Time.valueOf("13:00:00"),
            456,
            3,
            "new_user_id"
        )
        `when`(ReservationService.ajouter(nouvelleReservation)).thenReturn(nouvelleReservation)

        val response: ResponseEntity<Reservation> =
            ReservationControlleur.inscrireReservation(nouvelleReservation, JWT())

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(nouvelleReservation, response.body)
    }

    // Test pour inscrireReservation qui échoue
    @Test
    fun `Test inscrireReservation qui échoue`() {
        val nouvelleReservation = Reservation(
            "ASD123",
            Time.valueOf("10:00:00"),
            Time.valueOf("14:00:00"),
            789,
            5,
            null
        )
        `when`(ReservationService.ajouter(nouvelleReservation)).thenReturn(null)

        val response: ResponseEntity<Reservation> =
            ReservationControlleur.inscrireReservation(nouvelleReservation, JWT())

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }

    // Test pour modifierReservation avec succès
    @Test
    fun `Test modifierReservation avec succès`() {
        val reservationModifiee = Reservation(
            "ES100",
            Time.valueOf("11:00:00"),
            Time.valueOf("15:00:00"),
            1011,
            2,
            "modified_user_id"
        )
        `when`(ReservationService.modifier(reservationModifiee)).thenReturn(reservationModifiee)

        val response: ResponseEntity<Reservation> =
            ReservationControlleur.modifierReservation("ES100", reservationModifiee)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(reservationModifiee, response.body)
    }

    // Test pour modifierReservation avec une réservation inexistante
    @Test
    fun `Test modifierReservation avec une réservation inexistante`() {
        val reservationInexistante = Reservation(
            "ASC123",
            Time.valueOf("12:00:00"),
            Time.valueOf("16:00:00"),
            1314,
            1,
            "inexisting_user_id"
        )
        `when`(ReservationService.modifier(reservationInexistante)).thenReturn(reservationInexistante)

        val response: ResponseEntity<Reservation> =
            ReservationControlleur.modifierReservation("ASC123", reservationInexistante)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(reservationInexistante, response.body)
    }

    @Test
    fun `Test supprimerReservation pour une réservation existante`() {
        val reservationId = "ES100"
        val existingReservation = Reservation(
            reservationId,
            // ... Ajoutez les détails de la réservation existante
        )
        `when`(reservationService.chercherParCodeRéservation(reservationId)).thenReturn(existingReservation)

        val response: ResponseEntity<Any> = reservationController.supprimerReservation(reservationId)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `Test supprimerReservation pour une réservation inexistante`() {
        val nonExistingReservationId = "ID_INEXISTANT"
        `when`(reservationService.chercherParCodeRéservation(nonExistingReservationId)).thenReturn(null)

        val response: ResponseEntity<Any> = reservationController.supprimerReservation(nonExistingReservationId)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}