package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.data.Item

class FilmAdapter(
    private var items: List<Item>,
    private val clickListener: FilmItemClickListener,
    private val longClickListener: FilmItemLongClickListener
) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, clickListener, longClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name)
        private val genreTextView: TextView = itemView.findViewById(R.id.genre)
        private val yearTextView: TextView = itemView.findViewById(R.id.year)
        private val imageView: ImageView = itemView.findViewById(R.id.RV_Image)

        fun bind(
            item: Item,
            clickListener: FilmItemClickListener,
            longClickListener: FilmItemLongClickListener
        ) {
            nameTextView.text = item.nameRu
            yearTextView.text = item.year.toString()
            genreTextView.text = item.genres.joinToString { it.genre }

            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()

            Glide.with(itemView.context)
                .load(item.posterUrl)
                .apply(requestOptions)
                .into(imageView)

            itemView.setOnClickListener {
                clickListener.onFilmItemClick(item)
            }

            itemView.setOnLongClickListener {
                longClickListener.onFilmItemLongClick(item)
                true
            }
        }
    }
}

interface FilmItemClickListener {
    fun onFilmItemClick(item: Item)
}

interface FilmItemLongClickListener {
    fun onFilmItemLongClick(item: Item)
}