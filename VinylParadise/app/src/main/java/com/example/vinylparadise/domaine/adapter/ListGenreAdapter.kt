import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinylparadise.R
import com.example.vinylparadise.domaine.entité.Genre
import com.example.vinylparadise.domaine.adapter.VinylAdapter
import com.example.vinylparadise.domaine.service.VinylService
import com.example.vinylparadise.présentation.affichageVinylParGenre.GenreIVue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListGenreAdapter(
    private val genreList: List<Genre>,
    private val onAfficherToutClick: (Genre) -> Unit
) : RecyclerView.Adapter<ListGenreAdapter.ParentViewHolder>() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val service = VinylService()

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreTitle: TextView = itemView.findViewById(R.id.parent_item_title)
        val nestedRecyclerView: RecyclerView = itemView.findViewById(R.id.child_recycler_view)
        val afficherToutButton: TextView = itemView.findViewById(R.id.btnAffichertout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_vinyl_par_genre, parent, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val genre = genreList[position]
        holder.genreTitle.text = genre.nom

        holder.nestedRecyclerView.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)

        // Fetch vinyls asynchronously
        coroutineScope.launch {
            try {
                val vinyls = withContext(Dispatchers.IO) {
                    service.obtenirVinylParGenre(genre.nom)
                }
                // Limit to 3 items for display
                val limitedVinyls = vinyls.take(3)
                holder.nestedRecyclerView.adapter = VinylAdapter(limitedVinyls) { vinyl ->
                    val navController: NavController = Navigation.findNavController(holder.itemView)
                    val bundle = Bundle().apply {
                        putSerializable("selectedVinylId", vinyl.id)
                    }
                    navController.navigate(R.id.detailReservationVue, bundle)
                }
            } catch (e: Exception) {
                Log.e("ListGenreAdapter", "Error fetching vinyls for genre ${genre.nom}: ${e.message}")
            }
        }

        holder.afficherToutButton.setOnClickListener {
            val viewModel = ViewModelProvider(holder.itemView.context as FragmentActivity).get(
                GenreIVue::class.java)
            viewModel.genreselctionné.value = genre
            val navController = Navigation.findNavController(holder.itemView)
            navController.navigate(R.id.action_vinylsDisplayFragment_to_vinylsParGenre)
        }
    }

    override fun getItemCount(): Int = genreList.size
}
