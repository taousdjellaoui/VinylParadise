package com.example.vinylparadise.présentation.Recherche

import DatabaseHelper



class HistoriqueRecherchePrésentateur(
    private val vue: HistoriqueRechercheContract.View,
    private val baseDeDonnées: DatabaseHelper
) : HistoriqueRechercheContract.Presenter {

    override fun    chargerHistorique() {
        val historique = baseDeDonnées.obtenirToutesLesRecherches()
        vue.afficherHistorique(historique)
    }

    override fun effacerHistorique() {
        baseDeDonnées.effacerHistorique()
        vue.afficherMessage("Historique effacé")
        chargerHistorique()
    }
}
