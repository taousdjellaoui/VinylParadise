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
import com.example.vinylparadise.présentation.Modèle
import com.example.vinylparadise.domaine.service.VinylService

class VinylAdapter(
    private val vinylList: List<Vinyl>,
    private val onItemClicked: (Vinyl) -> Unit
) : RecyclerView.Adapter<VinylAdapter.VinylViewHolder>() {

    class VinylViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titre)
        val artistTextView: TextView = itemView.findViewById(R.id.artist)

        val priceTextView: TextView = itemView.findViewById(R.id.prix)
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val cardView: CardView = itemView.findViewById(R.id.phone_Cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VinylViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.vinyls_recycler, parent, false)
        return VinylViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VinylViewHolder, position: Int) {
        val modèle = Modèle()
        //val vinylService = VinylService()
        val currentVinyl = vinylList[position]
        Log.d("DEBUG", "Affichage du vinyle : ${currentVinyl.album.titre}")

        Glide.with(holder.itemView.context)
            .load(currentVinyl.album.image_url)
            .into(holder.imageView)

        holder.titleTextView.text = currentVinyl.album.titre
        holder.artistTextView.text = currentVinyl.album.artist.nom
        holder.priceTextView.text = "$${currentVinyl.prix}"

        holder.cardView.setOnClickListener {
            //vinylService.changerIdVinyl(currentVinyl?.id?:0)
            modèle.ajouterVinylTransfert(currentVinyl)

            holder.itemView.findNavController().navigate(R.id.detailReservationVue)
        }
    }




    override fun getItemCount(): Int {
        return vinylList.take(3).size
    }
}
