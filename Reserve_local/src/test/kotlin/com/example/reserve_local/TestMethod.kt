package com.example.reserve_local

import com.example.reserve_local.DAO.SouceDonnées
import com.example.reserve_local.Modele.Salle
import com.example.reserve_local.Services.SalleService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.context.event.annotation.BeforeTestMethod
import java.sql.Time
import org.springframework.web.bind.annotation.*


class TestMethod (val service : SalleService){
    private lateinit var sourceDonnées: SouceDonnées
    @BeforeTestMethod
    fun setUp() {

        sourceDonnées = SouceDonnées()
    }


}
