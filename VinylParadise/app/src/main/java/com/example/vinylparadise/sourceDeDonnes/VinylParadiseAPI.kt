package com.example.vinylparadise.sourceDeDonnes
import com.example.vinylparadise.domaine.entité.Artist
import com.example.vinylparadise.domaine.entité.Genre
import com.example.vinylparadise.domaine.entité.Utilisateur
import com.example.vinylparadise.domaine.entité.Vinyl
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VinylParadiseAPI {

    // Correspond au @GetMapping("/vinyls")
    @GET("vinyls")
    suspend fun chercherTousLesVinyls(): List<Vinyl>


    @GET("vinyls/{id}")
    suspend fun obtenirVinylParId(@Path("id") id: Int): Vinyl


    @GET("vinyls")
    suspend fun chercherVinylParTitreAlbum(@Query("titreAlbum") titre: String): List<Vinyl>


    @GET("vinyls")

    suspend fun chercherVinylParArtist(@Query("artist") artistName: String): List<Vinyl>


    @GET("vinyls")
    suspend fun chercherVinylsParGenre(@Query("genre") genre: String): List<Vinyl>


    @POST("vinyls")
    suspend fun ajouterVinyl(@Body vinyl: Vinyl): Vinyl


    @GET("genres")
    suspend  fun chercherToutGenres(): List<Genre>

    @GET("genres")
    suspend fun obtenirGenresParNom(@Query("nom") nom: String): List<Genre>
    @GET("utilisateurs")
    suspend fun ChercherToutUtilisateurs(): List<Utilisateur>

    @POST("utilisateurs")
    suspend fun ajouterUtilisateur(@Body utilisateur: Utilisateur): Utilisateur

    @GET("artistes")
  suspend fun chercherToutArtist(): List<Artist>


    @POST("artistes")
    suspend fun AjouterArtist(@Body artist: Artist): Artist

}