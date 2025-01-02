package com.example.vinylparadise.présentation.Recherche

interface HistoriqueRechercheContract {

    interface View {
        fun afficherHistorique(historique: List<String>)
        fun afficherMessage(message: String)
    }

    interface Presenter {
        fun chargerHistorique()
        fun effacerHistorique()
    }
}
