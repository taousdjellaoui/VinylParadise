package com.example.vinylparadise.présentation.affichageVinylParGenre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinylparadise.domaine.entité.Genre

class GenreIVue: ViewModel() {

        val genreselctionné = MutableLiveData<Genre>()
    }
