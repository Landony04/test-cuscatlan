package com.landony.cuscatlan.ui.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.landony.cuscatlan.R
import com.landony.cuscatlan.databinding.FragmentCommentsListBinding
import com.landony.cuscatlan.ui.MainActivity
import com.landony.cuscatlan.viewModels.PostsViewModel
import com.landony.domain.common.Result
import com.landony.domain.entities.CommentsByPostResultUI
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private var columnCount = 1
    private var isProgress = false
    private var idPost = ""

    private lateinit var fragmentCommentsBinding: FragmentCommentsListBinding
    private val postsViewModel: PostsViewModel by activityViewModels()
    private lateinit var commentsRecyclerViewAdapter: CommentsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            idPost = it.getString("idPost", "")
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCommentsBinding = FragmentCommentsListBinding.inflate(layoutInflater)
        return fragmentCommentsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity?)?.title = "COMMENTS"
        (activity as MainActivity?)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpObserver()
        postsViewModel.getCommentsByPost(idPost)
    }

    private fun setUpObserver() {
        postsViewModel.commentsInformation.observe(requireActivity()) {
            when (it) {
                is Result.Loading -> {
                    isProgress = true
                    fragmentCommentsBinding.listComments.visibility = View.GONE
                    fragmentCommentsBinding.progressBarComments.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    fragmentCommentsBinding.progressBarComments.visibility = View.GONE
                    fragmentCommentsBinding.listComments.visibility = View.VISIBLE
                    setupPostsAdapter(it.data)
                    isProgress = false
                }

                is Result.Failure -> {
                    fragmentCommentsBinding.progressBarComments.visibility = View.GONE
                    fragmentCommentsBinding.listComments.visibility = View.GONE
                    isProgress = false
                }
            }
        }
    }

    private fun setupPostsAdapter(allValues: ArrayList<CommentsByPostResultUI>) {
        if (allValues.isNotEmpty()) {
            fragmentCommentsBinding.listComments.setHasFixedSize(true)
            commentsRecyclerViewAdapter = CommentsRecyclerViewAdapter(
                allValues
            )

            fragmentCommentsBinding.listComments.adapter = commentsRecyclerViewAdapter
            commentsRecyclerViewAdapter.submitList(allValues)
        } else {
            fragmentCommentsBinding.listComments.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                (activity as MainActivity?)?.onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
    }
}