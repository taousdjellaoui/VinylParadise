package com.example.vinylparadise.présentation.AffichageVinyl

import android.util.Log
import com.example.vinylparadise.présentation.Modèle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VinylPrésentateur(private val vue: VinylsVue) {

    private val modele = Modèle()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun obtenirGenre() {
        coroutineScope.launch {
            try {
                val genres = modele.obtenirGenres()
                vue.afficherGenres(genres)
            } catch (e: Exception) {
                val errorMessage = "Erreur lors du chargement des genres: ${e.message}"
                vue.afficherErreur(errorMessage)
            }
        }
    }
}
