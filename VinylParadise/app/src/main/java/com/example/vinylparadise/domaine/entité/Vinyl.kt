package com.example.vinylparadise.domaine.entité



data class Vinyl(
    val id: Int?,
    val utilisateur: Utilisateur,
    val album: Album,
    val prix: Double,
    val description: String
)

