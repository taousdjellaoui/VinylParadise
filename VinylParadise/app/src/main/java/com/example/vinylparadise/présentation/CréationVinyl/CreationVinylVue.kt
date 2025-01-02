package com.example.vinylparadise.présentation.CréationVinyl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vinylparadise.R
import com.google.android.material.textfield.TextInputEditText

class CreationVinylVue : Fragment() {
    private val presenter = CreationVinylPrésentateur(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.creation_vinyl_fragment, container, false)

        val titleInput: TextInputEditText = binding.findViewById(R.id.textInputTitre)
        val artistInput: TextInputEditText = binding.findViewById(R.id.textInputArtist)
        val priceInput: TextInputEditText = binding.findViewById(R.id.textInputPrix)
        val descriptionInput: TextInputEditText = binding.findViewById(R.id.textInputDescription)
        val imageUrlInput: TextInputEditText = binding.findViewById(R.id.textInputImage)
        val usernameInput: TextInputEditText = binding.findViewById(R.id.textInputNom)
        val emailInput: TextInputEditText = binding.findViewById(R.id.email)
        val genreSpinner: Spinner = binding.findViewById(R.id.spinner_genre)

        presenter.obtenirListNomGenres()

        val saveButton: View = binding.findViewById(R.id.btnSave)
        saveButton.setOnClickListener {
            presenter.validerEtAjouterVinyl(
                requireContext(),
                titleInput.text.toString().trim(),
                artistInput.text.toString().trim(),
                priceInput.text.toString().trim(),
                imageUrlInput.text.toString().trim(),
                genreSpinner.selectedItem.toString(),
                descriptionInput.text.toString().trim(),
                usernameInput.text.toString().trim(),
                emailInput.text.toString().trim()
            )
        }

        return binding
    }

    fun afficherGenres(genres: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val genreSpinner: Spinner = view?.findViewById(R.id.spinner_genre)!!
        genreSpinner.adapter = adapter
    }

    fun afficherErreursDeValidation(errors: Map<String, String>) {
        val binding = view
        errors.forEach { (field, error) ->
            when (field) {
                "title" -> binding?.findViewById<TextInputEditText>(R.id.textInputTitre)?.error = error
                "artist" -> binding?.findViewById<TextInputEditText>(R.id.textInputArtist)?.error = error
                "price" -> binding?.findViewById<TextInputEditText>(R.id.textInputPrix)?.error = error
                "imageUrl" -> binding?.findViewById<TextInputEditText>(R.id.textInputImage)?.error = error
                "username" -> binding?.findViewById<TextInputEditText>(R.id.textInputNom)?.error = error
                "userEmail" -> binding?.findViewById<TextInputEditText>(R.id.email)?.error = error
            }
        }
    }

    fun afficherErreur(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun afficherSuccès(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun naviguerVersAffichageVinyls() {
        findNavController().navigate(R.id.action_creationVinylVue_to_vinylsDisplayFragment)
    }
}
