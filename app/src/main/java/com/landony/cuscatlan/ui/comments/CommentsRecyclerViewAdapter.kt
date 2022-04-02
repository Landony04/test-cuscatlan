package com.landony.cuscatlan.ui.comments

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.landony.cuscatlan.ui.comments.placeholder.FirsCommentViewHolder
import com.landony.cuscatlan.ui.comments.placeholder.PlaceholderContent.PlaceholderItem
import com.landony.cuscatlan.ui.comments.placeholder.SecondCommentViewHolder
import com.landony.domain.entities.CommentsByPostResultUI

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CommentsRecyclerViewAdapter(
    private val values: List<CommentsByPostResultUI>,
) : ListAdapter<CommentsByPostResultUI, RecyclerView.ViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> FirsCommentViewHolder(parent)

            else -> SecondCommentViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (((position % 2) == 0)) {
            true -> {
                val commentHolder = holder as FirsCommentViewHolder
                commentHolder.setDataComment(getItem(position).body, getItem(position).email)
            }

            else -> {
                val commentHolder = holder as SecondCommentViewHolder
                commentHolder.setDataComment(getItem(position).body, getItem(position).email)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    override fun getItemViewType(position: Int): Int {
        if (((position % 2) == 0)) return 1
        return 2
    }
}

private class UserDiffCallBack : DiffUtil.ItemCallback<CommentsByPostResultUI>() {
    override fun areItemsTheSame(
        oldItem: CommentsByPostResultUI,
        newItem: CommentsByPostResultUI
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: CommentsByPostResultUI,
        newItem: CommentsByPostResultUI
    ): Boolean =
        oldItem.id == newItem.id
}