package tech.arnav.monawallpapers

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.arnav.monawallpapers.http.MonaDatum

class MonaGridAdapter() : ListAdapter<MonaDatum, MonaViewHolder>(MonaItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonaViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MonaViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}


class MonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
}

class MonaItemDiffCallback : DiffUtil.ItemCallback<MonaDatum>() {
    override fun areItemsTheSame(oldItem: MonaDatum, newItem: MonaDatum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MonaDatum, newItem: MonaDatum): Boolean {
        return oldItem == newItem
    }

}