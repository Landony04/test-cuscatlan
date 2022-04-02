package com.landony.cuscatlan.ui.comments.placeholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.landony.cuscatlan.databinding.FragmentCommentsBinding

class FirsCommentViewHolder(
    private val fragmentHomeBinding: FragmentCommentsBinding
) : RecyclerView.ViewHolder(fragmentHomeBinding.root) {

    constructor(parent: ViewGroup) : this(
        FragmentCommentsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    fun setDataComment(body: String, userEmail: String) {
        fragmentHomeBinding.comment.text = body
        fragmentHomeBinding.email.text = userEmail
    }
}