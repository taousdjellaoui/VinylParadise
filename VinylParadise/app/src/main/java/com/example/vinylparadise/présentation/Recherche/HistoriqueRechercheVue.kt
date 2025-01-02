package com.example.vinylparadise.présentation.Recherche

import DatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinylparadise.R
import com.example.vinylparadise.présentation.HistoriqueRechercheAdaptateur

class HistoriqueRechercheVue : Fragment(), HistoriqueRechercheContract.View {

    private lateinit var recyclerHistorique: RecyclerView
    private lateinit var adaptateur: HistoriqueRechercheAdaptateur
    private lateinit var boutonEffacerHistorique: Button
    private lateinit var présentateur: HistoriqueRechercheContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vue = inflater.inflate(R.layout.fragment_historique_recherche, container, false)

        recyclerHistorique = vue.findViewById(R.id.recycler_historique)
        boutonEffacerHistorique = vue.findViewById(R.id.btn_effacer_historique)

        val gestionnaireDisposition = LinearLayoutManager(context)
        gestionnaireDisposition.reverseLayout = true
        gestionnaireDisposition.stackFromEnd = true
        recyclerHistorique.layoutManager = gestionnaireDisposition

        présentateur = HistoriqueRecherchePrésentateur(this, DatabaseHelper(requireContext()))

        présentateur.chargerHistorique()

        boutonEffacerHistorique.setOnClickListener {
            présentateur.effacerHistorique()
        }

        return vue
    }

    override fun afficherHistorique(historique: List<String>) {
        adaptateur = HistoriqueRechercheAdaptateur(historique)
        recyclerHistorique.adapter = adaptateur
    }

    override fun afficherMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
