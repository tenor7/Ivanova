package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kinopoiskapi.data.api.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MovieDetailsFragment : Fragment() {

    companion object {
        //private const val ARG_ID = "kinopoisk_id"
        private const val ARG_NAME_RU = "name_ru"
        private const val ARG_GENRES = "genres"
        private const val ARG_COUNTRY = "country"
        private const val ARG_POSTER_URL = "poster_url"
        private const val ARG_POSTER_URL_PREVIEW = "poster_url_preview"

        fun newInstance(nameOriginal: String, genres: String, country: String, posterUrl: String, posterUrlPreview: String): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val args = Bundle()
            //args.putString(ARG_ID, imdbId)
            args.putString(ARG_NAME_RU, nameOriginal)
            args.putString(ARG_GENRES, genres)
            args.putString(ARG_COUNTRY, country)
            args.putString(ARG_POSTER_URL, posterUrl)
            args.putString(ARG_POSTER_URL_PREVIEW, posterUrlPreview)
            fragment.arguments = args
            return fragment
        }
    }

    //private lateinit var apiService: ApiService
    //private lateinit var descriptionTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val imdbId = arguments?.getString(ARG_ID)
        val nameRu = arguments?.getString(ARG_NAME_RU)
        val genres = arguments?.getString(ARG_GENRES)
        val country = arguments?.getString(ARG_COUNTRY)
        val posterUrl = arguments?.getString(ARG_POSTER_URL)
        val posterUrlPreview = arguments?.getString(ARG_POSTER_URL_PREVIEW)


        if (nameRu != null && genres != null && country != null && posterUrl != null && posterUrlPreview != null) {
            // Установка названия фильма
            val titleTextView = view.findViewById<TextView>(R.id.text_title)
            titleTextView.text = nameRu
            // Установка жанра фильма
            val genreTextView = view.findViewById<TextView>(R.id.text_genre)
            genreTextView.text = "Жанр: $genres"
            // Установка страны производства фильма
            val countryTextView = view.findViewById<TextView>(R.id.text_country)
            countryTextView.text = "Страна: $country"

            val descriptionTextView = view.findViewById<TextView>(R.id.text_description)
            descriptionTextView.text = "Описание загружается..."

            // Загрузка постера фильма с использованием библиотеки Glide
            val posterImageView = view.findViewById<ImageView>(R.id.image_poster)
            Glide.with(this)
                .load(posterUrl)
                .into(posterImageView)
        }
    }
}