package com.example.vinylparadise.présentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinylparadise.R

class HistoriqueRechercheAdaptateur(private val historiqueRecherches: List<String>) :
    RecyclerView.Adapter<HistoriqueRechercheAdaptateur.VueHolder>() {

    class VueHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texteRecherche: TextView = itemView.findViewById(R.id.texte_element_historique)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VueHolder {
        val vue = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_historique_recherche, parent, false)
        return VueHolder(vue)
    }

    override fun onBindViewHolder(holder: VueHolder, position: Int) {
        holder.texteRecherche.text = historiqueRecherches[position]
    }

    override fun getItemCount(): Int = historiqueRecherches.size
}
