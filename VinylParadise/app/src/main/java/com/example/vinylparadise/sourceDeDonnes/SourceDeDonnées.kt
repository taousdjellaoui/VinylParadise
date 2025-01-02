package com.example.vinylparadise.sourceDeDonnes

import com.example.vinylparadise.domaine.entité.Genre
import com.example.vinylparadise.domaine.entité.Vinyl

interface SourceDeDonnées {
    fun obtenirVinyls() : List<Vinyl>
    fun obtenirGenres() : List<Genre>
    fun obtenirListNomGenres() : List<String>
    fun ajoutVinyl(vinyl :Vinyl) : Boolean
    fun trouverVenylParId(id : Int) : Vinyl?
}