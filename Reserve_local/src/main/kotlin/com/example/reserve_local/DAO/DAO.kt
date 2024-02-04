package com.example.reserve_local.DAO

import com.example.reserve_local.Modele.Reservation

interface DAO<T> {
    fun chercherTous(): List<T>
    fun chercherParCode(code: String): T?
    fun ajouter(element: T): T?

    fun modifier(element: T): T?
    fun supprimer(element: T): T?
    }


