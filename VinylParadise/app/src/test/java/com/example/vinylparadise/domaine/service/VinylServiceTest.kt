import com.example.vinylparadise.domaine.entité.*
import com.example.vinylparadise.domaine.service.VinylService
import com.example.vinylparadise.sourceDeDonnes.VinylParadiseAPI
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.reflect.Field

class VinylServiceTest {

    private lateinit var apiMock: VinylParadiseAPI
    private lateinit var vinylService: VinylService

    @Before
    fun setup() {
        apiMock = mock(VinylParadiseAPI::class.java)
        vinylService = VinylService()
        val apiField: Field = VinylService::class.java.getDeclaredField("api")
        apiField.isAccessible = true
        apiField.set(vinylService, apiMock)
    }

    @Test
    fun `Etant donné qu'un vinyl existe Lorsque je le récupère par son ID Alors il est retourné correctement`() = runTest {
        val artist = Artist(id = 1, nom = "Test Artist")
        val genre = Genre(id = 1, nom = "Rock")
        val album = Album(
            id = 1,
            titre = "Test Album",
            artist = artist,
            genres = listOf(genre),
            dateDeSortie = "2024-01-01",
            image_url = "https://example.com/album.jpg"
        )
        val utilisateur = Utilisateur(nom = "Test", prenom = "User", email = "user@test.com")
        val vinyl = Vinyl(
            id = 1,
            utilisateur = utilisateur,
            album = album,
            prix = 25.5,
            description = "Test Vinyl Description"
        )

        `when`(apiMock.obtenirVinylParId(1)).thenReturn(vinyl)

        val result = vinylService.obtenirVinylParId(1)

        assertEquals(vinyl, result)
        verify(apiMock).obtenirVinylParId(1)
    }

    @Test
    fun `Etant donné qu'il existe plusieurs vinyls Lorsque je récupère tous les vinyls Alors ils sont retournés correctement`() = runTest {
        val vinyl1 = Vinyl(
            id = 1,
            utilisateur = Utilisateur(nom = "Test1", prenom = "User1", email = "user1@test.com"),
            album = Album(1, "Album 1", Artist(1, "Artist 1"), listOf(Genre(1, "Rock")), "2023-01-01", "url1"),
            prix = 20.0,
            description = "Vinyl 1"
        )
        val vinyl2 = Vinyl(
            id = 2,
            utilisateur = Utilisateur(nom = "Test2", prenom = "User2", email = "user2@test.com"),
            album = Album(2, "Album 2", Artist(2, "Artist 2"), listOf(Genre(2, "Jazz")), "2023-02-01", "url2"),
            prix = 30.0,
            description = "Vinyl 2"
        )
        val vinylList = listOf(vinyl1, vinyl2)

        `when`(apiMock.chercherTousLesVinyls()).thenReturn(vinylList)

        val result = vinylService.obtenirTousLesVinyls()

        assertEquals(vinylList, result)
        verify(apiMock).chercherTousLesVinyls()
    }

    @Test
    fun `Etant donné un nouveau vinyl Lorsque j'ajoute le vinyl Alors il est ajouté correctement`() = runTest {
        // Prepare test data
        val newVinyl = Vinyl(
            id = null,
            utilisateur = Utilisateur(nom = "Test", prenom = "User", email = "user@test.com"),
            album = Album(
                id = 1,
                titre = "New Album",
                artist = Artist(id = 1, nom = "Artist Test"),
                genres = listOf(Genre(id = 1, nom = "Rock")),
                dateDeSortie = "2024-01-01",
                image_url = "https://example.com/new_album.jpg"
            ),
            prix = 40.0,
            description = "New Vinyl"
        )

        vinylService.ajouterVinyl(newVinyl)

        verify(apiMock).ajouterVinyl(newVinyl)
    }

    @Test
    fun `Etant donné qu'il existe des genres Lorsque je récupère tous les genres Alors ils sont retournés correctement`() = runTest {
        val genres = listOf(Genre(id = 1, nom = "Rock"), Genre(id = 2, nom = "Jazz"))

        `when`(apiMock.chercherToutGenres()).thenReturn(genres)

        val result = vinylService.obtenirTousLesGenres()

        assertEquals(genres, result)
        verify(apiMock).chercherToutGenres()
    }


    @Test
    fun `Etant donné des vinyls filtrés par genre Lorsque je récupère les vinyls par genre Alors seuls les vinyls du genre sont retournés`() = runTest {
        val genre = Genre(id = 1, nom = "Rock")
        val vinyl = Vinyl(
            id = 1,
            utilisateur = Utilisateur(nom = "Test", prenom = "User", email = "user@test.com"),
            album = Album(1, "Rock Album", Artist(1, "Rock Artist"), listOf(genre), "2024-01-01", "url"),
            prix = 25.0,
            description = "Vinyl Rock"
        )
        val vinylList = listOf(vinyl)

        `when`(apiMock.chercherVinylsParGenre("Rock")).thenReturn(vinylList)

        val result = vinylService.obtenirVinylParGenre("Rock")

        assertEquals(vinylList, result)
        verify(apiMock).chercherVinylsParGenre("Rock")
    }
}