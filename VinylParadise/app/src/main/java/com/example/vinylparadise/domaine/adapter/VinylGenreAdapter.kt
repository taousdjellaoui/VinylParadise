package com.example.vinylparadise.domaine.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinylparadise.R
import com.example.vinylparadise.domaine.entité.Vinyl
import com.example.vinylparadise.domaine.adapter.VinylAdapter.VinylViewHolder
import com.example.vinylparadise.présentation.Modèle

class VinylGenreAdapter (
    private val vinylList: List<Vinyl>,
    private val onItemClicked: (Vinyl) -> Unit
) : RecyclerView.Adapter<VinylGenreAdapter.VinylViewHolder>() {

    class VinylViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageVinyl)
        val nameTextView: TextView = itemView.findViewById(R.id.titreVinyl)
        val artistTextView: TextView = itemView.findViewById(R.id.artistVinyl)
        val priceTextView: TextView = itemView.findViewById(R.id.prixVinyl)
        val cardView: CardView = itemView.findViewById(R.id.phone_CardviewGenre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VinylViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vinylsgenre_recycler, parent, false)
        return VinylViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: com.example.vinylparadise.domaine.adapter.VinylGenreAdapter.VinylViewHolder, position: Int) {
        val modèle = Modèle()
        val currentVinyl = vinylList[position]
        Glide.with(holder.itemView.context)
            .load(currentVinyl.album.image_url)
            .into(holder.imageView)

        holder.nameTextView.text = currentVinyl.album.titre
        holder.artistTextView.text = currentVinyl.album.artist.nom
        holder.priceTextView.text = "$${currentVinyl.prix}"

        holder.cardView.setOnClickListener {
            modèle.ajouterVinylTransfert(currentVinyl)
            holder.itemView.findNavController().navigate(R.id.detailReservationVue)
            Log.d("DEBUG", "Card clicked: ${currentVinyl.album.titre}")
        }
    }

    override fun getItemCount() = vinylList.size
}