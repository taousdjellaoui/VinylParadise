package com.example.vinylparadise.présentation

import android.util.Log
import com.example.vinylparadise.domaine.entité.Artist
import com.example.vinylparadise.domaine.entité.Genre
import com.example.vinylparadise.domaine.entité.Utilisateur
import com.example.vinylparadise.domaine.entité.Vinyl
import com.example.vinylparadise.domaine.service.VinylService

class Modèle {
    private val service = VinylService()

companion object{
    lateinit var vinylTransfert: Vinyl
}


    suspend fun ajoutVinyl(vinyl: Vinyl) {

            service.ajouterVinyl(vinyl)

    }
    suspend fun ajoutArtist(artist: Artist) {

            service.ajouterArtist(artist)

    }
    suspend fun ajoutUtlisateur(utilisateur: Utilisateur) {

            service.ajouterUtilisateur(utilisateur)

    }
    suspend fun obtenirGenresParNom(nom:String): List<Genre> {
       return service.obtenirGenresParNom(nom)
    }

    suspend  fun obtenirListGenreNom(): List<String> {

        return   service.obtenirTousLesGenres().map { it.nom }

    }


   suspend fun obtenirGenres(): List<Genre> {

         return   service.obtenirTousLesGenres()

    }
    suspend fun obtenirVinylParGenre(genre: String): List<Vinyl> {
        return service.obtenirVinylParGenre(genre)

    }


     fun ajouterVinylTransfert(vinyl: Vinyl) {
         Log.d("VinylTransfert modele", "Vinyl fetched: $vinyl")
        vinylTransfert = vinyl
    }


     suspend fun retrouverVinylTransfert(): Vinyl {
        val vinyl = service.obtenirVinylParId(vinylTransfert.id?:1)
         Log.d("VinylTransfert modele", "Vinyl fetched: $vinyl")
        return  vinyl
    }


    suspend  fun obetenirVinylFiltrer(query: String): List<Vinyl> {
        return service.obtenirTousLesVinyls().filter { vinyl ->
                vinyl.album.titre.contains(query, ignoreCase = true) ||
                        vinyl.album.artist.nom.contains(query, ignoreCase = true)

        }
    }

}