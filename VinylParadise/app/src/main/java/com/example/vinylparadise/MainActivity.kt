package com.example.vinylparadise

import DatabaseHelper
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ajoutImage: ImageView = findViewById(R.id.imgAjoutVinyl)
        val logoImage: ImageView = findViewById(R.id.home)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val db = DatabaseHelper(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    db.AjouterRecherche(query)
                    afficherResultatsRecherche(it)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    afficherResultatsRecherche(it)
                }
                return false
            }
        })

        val boutonHistoriqueRecherche = findViewById<Button>(R.id.btn_historique_recherche)
        boutonHistoriqueRecherche.setOnClickListener {
            try {
                findNavController(R.id.fragmentContainerView2).navigate(R.id.fragment_historique_recherche)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        ajoutImage.setOnClickListener {
            try {

                findNavController(R.id.fragmentContainerView2).navigate(R.id.creationVinylVue)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        logoImage.setOnClickListener {
            try {
                findNavController(R.id.fragmentContainerView2).navigate(R.id.vinylsDisplayFragment)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun afficherResultatsRecherche(query: String) {
        val bundle = Bundle().apply {
            putString("query", query)
        }
        findNavController(R.id.fragmentContainerView2).navigate(R.id.resultatFragment, bundle)
    }
}