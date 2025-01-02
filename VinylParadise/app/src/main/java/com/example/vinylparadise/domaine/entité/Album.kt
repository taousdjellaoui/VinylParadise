package com.example.vinylparadise.domaine.entité



data class Album(
    val id: Int?,
    val titre: String,
    val artist: Artist,
    val genres: List<Genre>,
    val dateDeSortie: String,
    val image_url: String
)