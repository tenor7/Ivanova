package com.example.myapplication


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.data.api.ApiService
import com.example.myapplication.data.Item
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class PopularFragment : Fragment(), FilmItemClickListener, FilmItemLongClickListener {
    private lateinit var apiService: ApiService
    private lateinit var filmAdapter: FilmAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val baseUrl = "https://kinopoiskapiunofficial.tech"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        setupRecyclerView()

        fetchTopFilms()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        filmAdapter = FilmAdapter(emptyList(), this, this)
        recyclerView.adapter = filmAdapter
    }

    private fun fetchTopFilms() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val type = "TOP_100_POPULAR_FILMS"
                val response = apiService.getTopFilms(type).execute()

                if (response.isSuccessful) {
                    val filmsResponse = response.body()
                    filmsResponse?.let {
                        withContext(Dispatchers.Main) {
                            filmAdapter.updateItems(it.films)
                        }
                    }
                } else {
                    // Обработка ошибки
                    withContext(Dispatchers.Main) {
                        showNetworkError()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                // Обработка ошибки сети
                withContext(Dispatchers.Main) {
                    showNetworkError()
                }
            }
        }
    }

    private fun showNetworkError() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Network Error")
            .setMessage("There was an error loading data. Please check your network connection and try again.")
            .setPositiveButton("Retry") { dialog, _ ->
                fetchTopFilms()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onFilmItemClick(item: Item) {
        val fragment = MovieDetailsFragment.newInstance(
            item.nameRu,
            item.genres.joinToString { it.genre },
            item.countries.joinToString { it.country },
            item.posterUrl,
            item.posterUrlPreview
        )
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment)?.addToBackStack(null)
            ?.commit()
    }

    override fun onFilmItemLongClick(item: Item) {
        // Действия при долгом нажатии на элемент списка (добавление в избранное, например)
        Toast.makeText(requireContext(), "Added to favorites: ${item.nameRu}", Toast.LENGTH_SHORT).show()
        // Можно здесь изменить значение isFavorite для элемента и обновить адаптер
        // item.isFavorite = !item.isFavorite
        // filmAdapter.notifyDataSetChanged()
    }
}