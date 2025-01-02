package com.example.vinylparadise.présentation.AffichageVinyl

import ListGenreAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinylparadise.R
import com.example.vinylparadise.domaine.entité.Genre

class VinylsVue : Fragment() {

    private lateinit var listGenreAdapter: ListGenreAdapter
    private lateinit var parentRecyclerView: RecyclerView
    private val présentateur = VinylPrésentateur(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vinyls_display, container, false)


        parentRecyclerView = view.findViewById(R.id.parent_recycler_view)
        parentRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        présentateur.obtenirGenre()

        return view
    }


    fun afficherGenres(genres: List<Genre>) {
        listGenreAdapter = ListGenreAdapter(genres) { genre ->
            
            findNavController().navigate(R.id.action_vinylsDisplayFragment_to_vinylsParGenre)
        }
        parentRecyclerView.adapter = listGenreAdapter
    }


    fun afficherErreur(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
