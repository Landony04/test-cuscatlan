package com.landony.cuscatlan.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.landony.cuscatlan.databinding.FragmentHomeListBinding
import com.landony.cuscatlan.viewModels.PostsViewModel
import com.landony.domain.common.Result
import com.landony.domain.entities.PostsUI
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
open class HomeFragment : Fragment() {

    private var columnCount = 1
    private var isProgress = false

    private val postsViewModel: PostsViewModel by activityViewModels()
    private lateinit var homeBinding: FragmentHomeListBinding
    private lateinit var adapter: HomeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeListBinding.inflate(inflater, container, false)

        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        postsViewModel.getAllPosts()
    }

    private fun setUpObserver() {
        postsViewModel.postsInformation.observe(requireActivity()) {
            when (it) {
                is Result.Loading -> {
                    isProgress = true
                    homeBinding.list.visibility = View.GONE
                    homeBinding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    homeBinding.progressBar.visibility = View.GONE
                    homeBinding.list.visibility = View.VISIBLE
                    setupPostsAdapter(it.data)
                    isProgress = false
                }

                is Result.Failure -> {
                    homeBinding.progressBar.visibility = View.GONE
                    homeBinding.list.visibility = View.GONE
                    isProgress = false
                }
            }
        }
    }

    private fun setupPostsAdapter(allValues: ArrayList<PostsUI>) {
        if (allValues.isNotEmpty()) {
            homeBinding.list.setHasFixedSize(true)
            adapter = HomeRecyclerViewAdapter(
                allValues
            ) {

            }

            homeBinding.list.adapter = adapter
            adapter.submitList(allValues)
        } else {
            homeBinding.list.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}