package com.example.vinylparadise.présentation.DétailsVinyl

import android.util.Log
import android.view.View
import com.example.vinylparadise.domaine.entité.Vinyl
import com.example.vinylparadise.présentation.Modèle

class DetailReservationPresentateur(private val fragment: DetailReservationVue) {
    val modele = Modèle()
    suspend fun chargerVinylDetails(vinylId: Int, vue: View) {
        try {

            val vinyl = modele.retrouverVinylTransfert()
            fragment.AfficherleVinylEtActiverLeButton(vue)
        } catch (e: Exception) {
            fragment.afficherErreur("Erreur lors de la récupération des détails du vinyle")
        }
    }

    suspend fun trouverVinylTransfert(): Vinyl {

        val vinyl = modele.retrouverVinylTransfert()

return vinyl
    }


}