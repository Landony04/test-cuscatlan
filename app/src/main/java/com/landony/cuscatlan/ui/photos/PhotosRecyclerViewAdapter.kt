package com.landony.cuscatlan.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.landony.cuscatlan.databinding.FragmentPhotosBinding
import com.landony.domain.entities.PhotosByPostUI
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PhotosRecyclerViewAdapter(
    private val values: ArrayList<PhotosByPostUI>
) : ListAdapter<PhotosByPostUI, PhotosRecyclerViewAdapter.ViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentPhotosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val photos = values[position]

            Picasso.get()
                .load(photos.url)
                .fit()
                .into(binding.image)
        }
    }
}

private class UserDiffCallBack : DiffUtil.ItemCallback<PhotosByPostUI>() {
    override fun areItemsTheSame(
        oldItem: PhotosByPostUI,
        newItem: PhotosByPostUI
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: PhotosByPostUI,
        newItem: PhotosByPostUI
    ): Boolean =
        oldItem.id == newItem.id
}