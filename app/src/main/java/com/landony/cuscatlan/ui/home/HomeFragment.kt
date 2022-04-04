package com.landony.cuscatlan.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.landony.cuscatlan.databinding.FragmentHomeListBinding
import com.landony.cuscatlan.ui.MainActivity
import com.landony.cuscatlan.viewModels.PostsViewModel
import com.landony.domain.common.Result
import com.landony.domain.entities.PostsUI
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
open class HomeFragment : Fragment(), ItemSelectedCallback, TextWatcher {

    private var columnCount = 1
    private var isProgress = false

    private val postsViewModel: PostsViewModel by activityViewModels()
    private lateinit var homeBinding: FragmentHomeListBinding
    private lateinit var adapter: HomeRecyclerViewAdapter

    private var listValues: ArrayList<PostsUI> = arrayListOf()
    private var filteredList: ArrayList<PostsUI> = arrayListOf()

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
        (activity as MainActivity?)?.title = "POSTS"
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        homeBinding.searchPostsEditText.addTextChangedListener(this)

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
                    listValues.addAll(it.data)
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
                allValues,
                this
            )

            homeBinding.list.adapter = adapter
            adapter.submitList(allValues)
        } else {
            homeBinding.list.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
    }

    override fun itemSelected(idPost: String, nextView: String) {
        when (nextView) {
            HomeRecyclerViewAdapter.VIEW_PHOTOS -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPhotosFragment(idPost = idPost)
                )
            }

            else -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToCommentFragment(idPost = idPost)
                )
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (query.toString().length > 3 && !isProgress) {
            filteredList.clear()
            listValues.forEach {
                if (it.title.contains(query.toString())) {
                    filteredList.add(it)
                }
            }
            adapter.updateList(filteredList)
        } else if (query.toString().length < 3 && listValues.isNotEmpty()) {
            adapter.updateList(listValues)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}