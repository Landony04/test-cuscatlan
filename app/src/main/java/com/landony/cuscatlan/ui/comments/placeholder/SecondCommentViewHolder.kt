package com.landony.cuscatlan.ui.comments.placeholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.landony.cuscatlan.databinding.FragmentHomeOutgoingBinding

class SecondCommentViewHolder(
    private val fragmentHomeOutgoingBinding: FragmentHomeOutgoingBinding
) : RecyclerView.ViewHolder(fragmentHomeOutgoingBinding.root) {

    constructor(parent: ViewGroup) : this(
        FragmentHomeOutgoingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    fun setDataComment(body: String, userEmail: String) {
        fragmentHomeOutgoingBinding.comment.text = body
        fragmentHomeOutgoingBinding.email.text = userEmail
    }
}