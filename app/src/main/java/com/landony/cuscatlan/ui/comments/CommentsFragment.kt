package com.landony.cuscatlan.ui.comments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
class CommentsFragment : Fragment(), TextWatcher {

    private var columnCount = 1
    private var idPost = ""

    private lateinit var fragmentCommentsBinding: FragmentCommentsListBinding
    private val postsViewModel: PostsViewModel by activityViewModels()
    private lateinit var commentsRecyclerViewAdapter: CommentsRecyclerViewAdapter

    private var listValues: ArrayList<CommentsByPostResultUI> = arrayListOf()
    private var filteredList: ArrayList<CommentsByPostResultUI> = arrayListOf()

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

        fragmentCommentsBinding.searchCommentsEditText.addTextChangedListener(this)

        setUpObserver()
        postsViewModel.getCommentsByPost(idPost)
    }

    private fun setUpObserver() {
        postsViewModel.commentsInformation.observe(requireActivity()) {
            when (it) {
                is Result.Loading -> {
                    fragmentCommentsBinding.listComments.visibility = View.GONE
                    fragmentCommentsBinding.progressBarComments.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    fragmentCommentsBinding.progressBarComments.visibility = View.GONE
                    fragmentCommentsBinding.listComments.visibility = View.VISIBLE
                    listValues.addAll(it.data)
                    setupPostsAdapter(it.data)
                }

                is Result.Failure -> {
                    fragmentCommentsBinding.progressBarComments.visibility = View.GONE
                    fragmentCommentsBinding.listComments.visibility = View.GONE
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

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (query.toString().length > 3) {
            filteredList.clear()
            listValues.forEach {
                if (it.body.contains(query.toString())) {
                    filteredList.add(it)
                }
            }
            commentsRecyclerViewAdapter.updateList(filteredList)
        } else if (query.toString().length < 3 && listValues.isNotEmpty()) {
            commentsRecyclerViewAdapter.updateList(listValues)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}