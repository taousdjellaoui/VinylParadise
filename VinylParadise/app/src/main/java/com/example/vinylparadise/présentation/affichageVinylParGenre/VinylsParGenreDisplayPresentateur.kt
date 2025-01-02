package com.example.vinylparadise.présentation.affichageVinylParGenre

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.vinylparadise.R
import com.example.vinylparadise.domaine.adapter.VinylGenreAdapter
import com.example.vinylparadise.domaine.entité.Genre
import com.example.vinylparadise.domaine.entité.Vinyl
import com.example.vinylparadise.présentation.Modèle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VinylsParGenreDisplayPresentateur(private val vue: VinylsParGenreDisplayVue) {

    private val modèle = Modèle()

    suspend fun gérerAffichageVinyles(genre: Genre?) {
        if (genre == null) {
            vue.afficherErreur("Le genre sélectionné est introuvable.")
            return
        }

        val titreGenre = genre.nom
        vue.afficherTitreGenre(titreGenre)

        try {
            val vinyles = withContext(Dispatchers.IO) {
                modèle.obtenirVinylParGenre(titreGenre)
            }

            if (vinyles.isEmpty()) {
                vue.afficherErreur("Aucun vinyle trouvé pour ce genre.")
            } else {
                val adapter = VinylGenreAdapter(vinyles) { vinyl ->
                    gérerNavigationVinyl(vinyl)
                }
                vue.afficherVinylesParGenre(adapter)
            }
        } catch (e: Exception) {
            vue.afficherErreur("Erreur lors de la récupération des vinyles.")
        }
    }

    private fun gérerNavigationVinyl(vinyl: Vinyl) {
        val bundle = Bundle().apply {
            putSerializable("selectedVinylid", vinyl.id)
        }
        val navController = vue.requireActivity().let {
            androidx.navigation.Navigation.findNavController(it, R.id.genre_recycler)
        }
        navController.navigate(R.id.detailReservationVue, bundle)
    }
}
