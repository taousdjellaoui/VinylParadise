package com.example.vinylparadise.présentation.affichageVinylParGenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinylparadise.R
import com.example.vinylparadise.domaine.adapter.VinylGenreAdapter
import kotlinx.coroutines.launch

class VinylsParGenreDisplayVue : Fragment() {

    private lateinit var vinylRecyclerView: RecyclerView
    private lateinit var myTextView: TextView
    private lateinit var presentateur: VinylsParGenreDisplayPresentateur

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vinyls_par_genre, container, false)

        vinylRecyclerView = view.findViewById(R.id.genre_recycler)
        vinylRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        myTextView = view.findViewById(R.id.titreGenre)

        presentateur = VinylsParGenreDisplayPresentateur(this)

        val viewModel = ViewModelProvider(requireActivity()).get(GenreIVue::class.java)
        viewModel.genreselctionné.observe(viewLifecycleOwner) { genre ->
            lifecycleScope.launch {
                presentateur.gérerAffichageVinyles(genre)
            }
        }

        return view
    }

    fun afficherVinylesParGenre(adapter: VinylGenreAdapter) {
        vinylRecyclerView.adapter = adapter
        myTextView.visibility = View.GONE
        vinylRecyclerView.visibility = View.VISIBLE
    }

    fun afficherTitreGenre(titre: String) {
        myTextView.text = titre
    }

    fun afficherErreur(message: String) {
        myTextView.text = message
        myTextView.visibility = View.VISIBLE
        vinylRecyclerView.visibility = View.GONE
    }
}
