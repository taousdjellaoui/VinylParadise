package com.example.vinylparadise.domaine.service
import android.util.Log
import com.example.vinylparadise.domaine.entité.Artist
import com.example.vinylparadise.domaine.entité.Genre
import com.example.vinylparadise.domaine.entité.Utilisateur
import com.example.vinylparadise.domaine.entité.Vinyl
import com.example.vinylparadise.sourceDeDonnes.VinylParadiseAPISingelton


class VinylService {
    private var api = VinylParadiseAPISingelton.instance
    private var idVinyle = 0

    fun obtinirIdVinyl(): Int {
        return idVinyle
    }

    fun changerIdVinyl(id: Int){
        idVinyle = id
    }

    suspend fun obtenirVinylParId(id:Int): Vinyl{
        return api.obtenirVinylParId(id)
    }

    suspend fun obtenirTousLesVinyls(): List<Vinyl> {
        return api.chercherTousLesVinyls()
    }

    suspend fun ajouterVinyl(vinyl: Vinyl) {

        api.ajouterVinyl(vinyl)
    }

    suspend fun obtenirTousLesGenres(): List<Genre> {
        return api.chercherToutGenres()
    }
    suspend fun obtenirGenresParNom(nom:String): List<Genre> {
        return try {
            val allGenres = this.obtenirTousLesGenres()
            val matchingGenre = allGenres.find { it.nom.equals(nom, ignoreCase = true) }

            if (matchingGenre != null) {
                listOf(matchingGenre)
            } else {

                throw Exception("Genre '$nom' not found.")
            }
        } catch (e: Exception) {
            Log.e("ERROR", "Error fetching genres: ${e.message}", e)
            emptyList()
        }
    }
    suspend fun ajouterArtist(artist:Artist){
        api.AjouterArtist(artist)
    }
    suspend fun ajouterUtilisateur(utilisateur: Utilisateur){
        api.ajouterUtilisateur(utilisateur)
    }
    suspend fun obtenirTousLesArtistes(): List<Artist> {
        return api.chercherToutArtist()
    }
    suspend fun obtenirVinylParGenre(genre :String): List<Vinyl> {
        return api.chercherVinylsParGenre(genre)
    }
    suspend fun obtenirNomDesGenre(): List<String> {
       val genres = api.chercherToutGenres()
        val listGenreNom = mutableListOf<String>()

        for (genre in genres) {
            listGenreNom.add(genre.nom)
        }

        return listGenreNom

    }
}