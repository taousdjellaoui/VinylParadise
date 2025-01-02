package com.example.vinylparadise.présentation.CréationVinyl

import android.content.Context
import android.util.Patterns
import com.example.vinylparadise.R
import com.example.vinylparadise.domaine.entité.*
import com.example.vinylparadise.présentation.Modèle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate

class CreationVinylPrésentateur(private val vue: CreationVinylVue) {
    private val vinylModele = Modèle()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun obtenirListNomGenres() {
        coroutineScope.launch {
            try {
                val genres = withContext(Dispatchers.IO) { vinylModele.obtenirListGenreNom() }
                vue.afficherGenres(genres)
            } catch (e: Exception) {
                vue.afficherErreur("Erreur lors du chargement des genres: ${e.message}")
            }
        }
    }

    fun validerEtAjouterVinyl(
        context: Context,
        titre: String,
        artiste: String,
        prix: String,
        urlImage: String,
        genreSélectionné: String,
        description: String,
        nomUtilisateur: String,
        emailUtilisateur: String
    ) {
        coroutineScope.launch {
            val erreursDeValidation = validerChamps(context, titre, artiste, prix, urlImage, nomUtilisateur, emailUtilisateur)

            if (erreursDeValidation.isNotEmpty()) {
                vue.afficherErreursDeValidation(erreursDeValidation)
                return@launch
            }

            val urlImageValide = withContext(Dispatchers.IO) { validerUrlImage(urlImage) }
            if (!urlImageValide) {
                vue.afficherErreur(context.getString(R.string.erreur_invalid_url))
                return@launch
            }

            try {
                val utilisateur = Utilisateur(nomUtilisateur, nomUtilisateur, emailUtilisateur)
                val artisteObj = Artist(null, artiste)
                val genres = vinylModele.obtenirGenresParNom(genreSélectionné)
                if (genres.isEmpty()) {
                    throw Exception("Genre '$genreSélectionné' est invalide ou introuvable.")
                }

                val nouveauVinyl = Vinyl(
                    id = null,
                    album = Album(
                        id = null,
                        titre = titre,
                        artist = artisteObj,
                        genres = genres,
                        dateDeSortie = LocalDate.now().toString(),
                        image_url = urlImage
                    ),
                    utilisateur = utilisateur,
                    prix = prix.toDouble(),
                    description = description
                )

                vinylModele.ajoutVinyl(nouveauVinyl)
                vue.afficherSuccès(context.getString(R.string.toast_vinyl_ajout))
                vue.naviguerVersAffichageVinyls()

            } catch (e: Exception) {
                vue.afficherErreur("Erreur lors de l'ajout du vinyle: ${e.message}")
            }
        }
    }

    private fun validerChamps(
        context: Context,
        titre: String,
        artiste: String,
        prix: String,
        urlImage: String,
        nomUtilisateur: String,
        emailUtilisateur: String
    ): Map<String, String> {
        val erreurs = mutableMapOf<String, String>()

        if (titre.isEmpty()) {
            erreurs["titre"] = context.getString(R.string.erreur_titre_requis)
        }
        if (artiste.isEmpty()) {
            erreurs["artiste"] = context.getString(R.string.erreur_artiste_requis)
        }
        if (prix.isEmpty() || prix.toDoubleOrNull() == null) {
            erreurs["prix"] = context.getString(R.string.erreur_prix_requis)
        }
        if (urlImage.isEmpty()) {
            erreurs["urlImage"] = context.getString(R.string.erreur_url_image_requise)
        }
        if (nomUtilisateur.isEmpty()) {
            erreurs["nomUtilisateur"] = context.getString(R.string.erreur_nom_utilisateur_requis)
        }
        if (emailUtilisateur.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailUtilisateur).matches()) {
            erreurs["emailUtilisateur"] = context.getString(R.string.erreur_email_requis)
        }

        return erreurs
    }

    private fun validerUrlImage(url: String): Boolean {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.apply {
                connectTimeout = 10000
                readTimeout = 10000
                requestMethod = "GET"
                connect()
            }
            connection.responseCode == HttpURLConnection.HTTP_OK && connection.contentType.startsWith("image/")
        } catch (e: Exception) {
            false
        }
    }
}