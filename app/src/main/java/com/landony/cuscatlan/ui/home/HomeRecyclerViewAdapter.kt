package com.landony.cuscatlan.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.landony.cuscatlan.databinding.FragmentHomeBinding
import com.landony.domain.entities.PostsUI

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class HomeRecyclerViewAdapter(
    private val values: ArrayList<PostsUI>,
    private val onCallInfoPosts: (postsId: String) -> Unit
) : ListAdapter<PostsUI, HomeRecyclerViewAdapter.ViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder internal constructor(val binding: FragmentHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.title

        fun bind(position: Int) {
            val posts = values[position]

            binding.title.text = posts.title
            binding.body.text = posts.body

            binding.photos.setOnClickListener {
                onCallInfoPosts(posts.id.toString())
            }

            binding.comments.setOnClickListener {
                onCallInfoPosts(posts.id.toString())
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}

private class UserDiffCallBack : DiffUtil.ItemCallback<PostsUI>() {
    override fun areItemsTheSame(
        oldItem: PostsUI,
        newItem: PostsUI
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: PostsUI,
        newItem: PostsUI
    ): Boolean =
        oldItem.id == newItem.id
}