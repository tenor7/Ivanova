package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var btnPopular: Button
    private lateinit var btnFavorite: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPopular = findViewById(R.id.btnPopular)
        btnFavorite = findViewById(R.id.btnFavorite)

        btnPopular.isSelected = true

        btnPopular.setOnClickListener {
            btnPopular.isSelected = true
            btnFavorite.isSelected = false
            switchFragment(PopularFragment())
        }

        btnFavorite.setOnClickListener {
            btnPopular.isSelected = false
            btnFavorite.isSelected = true
            switchFragment(FavoriteFragment())
        }

        // По умолчанию показывается фрагмент "Популярное"
        switchFragment(PopularFragment())
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun showNetworkError() {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, "Ошибка сети. Проверьте подключение и повторите попытку", Snackbar.LENGTH_LONG)
            .setAction("Повторить") {
                // Повторная попытка загрузки данных
                // Например, вызов функции для повторной загрузки фрагмента
            }
            .show()
    }
}