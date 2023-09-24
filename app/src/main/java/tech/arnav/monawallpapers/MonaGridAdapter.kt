package tech.arnav.monawallpapers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.load
import tech.arnav.monawallpapers.databinding.ListItemMonaCardBinding
import tech.arnav.monawallpapers.http.MonaData
import tech.arnav.monawallpapers.http.MonaDatum

class MonaGridAdapter() : ListAdapter<MonaDatum, MonaViewHolder>(MonaItemDiffCallback()) {

    private val MonaData: MonaData = emptyArray()

    fun setMonaData(monaData: MonaData) {
        submitList(monaData.toList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMonaCardBinding.inflate(layoutInflater, parent, false)
        val itemView = binding.root
        return MonaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MonaViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}


class MonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ListItemMonaCardBinding.bind(itemView)
    fun bind(item: MonaDatum) {
        binding.tvMonaTitle.text = item.title
        binding.ivMonaImage.load(item.imgURL) {
            placeholder(R.drawable.placeholder)
            crossfade(true)
            crossfade(1000)
        }
        binding.tvMonaArtist.text = item.artist
    }
}

class MonaItemDiffCallback : DiffUtil.ItemCallback<MonaDatum>() {
    override fun areItemsTheSame(oldItem: MonaDatum, newItem: MonaDatum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MonaDatum, newItem: MonaDatum): Boolean {
        return oldItem == newItem
    }

}