package com.example.vinylparadise.présentation.Recherche

import com.example.vinylparadise.domaine.entité.Vinyl
import com.example.vinylparadise.présentation.Modèle

class recherchePresentateur {

    val Modèle = Modèle()

    suspend fun obtenirVinylFiltrer(query: String): List<Vinyl> {
        return Modèle.obetenirVinylFiltrer(query)
    }
}
