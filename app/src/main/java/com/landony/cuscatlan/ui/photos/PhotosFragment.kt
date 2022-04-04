package com.landony.cuscatlan.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.landony.cuscatlan.R
import com.landony.cuscatlan.databinding.FragmentPhotoListBinding
import com.landony.cuscatlan.ui.MainActivity
import com.landony.cuscatlan.viewModels.PostsViewModel
import com.landony.domain.common.Result
import com.landony.domain.entities.PhotosByPostUI
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private var columnCount = 2
    private var isProgress = false
    private var idPost = ""

    private lateinit var fragmentCommentsBinding: FragmentPhotoListBinding
    private val postsViewModel: PostsViewModel by activityViewModels()
    private lateinit var photosRecyclerViewAdapter: PhotosRecyclerViewAdapter

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
    ): View? {
        fragmentCommentsBinding = FragmentPhotoListBinding.inflate(layoutInflater, container, false)
        return fragmentCommentsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.title = "PHOTOS"
        (activity as MainActivity?)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpObserver()
        postsViewModel.getPhotosByPost(idPost)
    }

    private fun setUpObserver() {
        postsViewModel.photosInformation.observe(requireActivity()) {
            when (it) {
                is Result.Loading -> {
                    isProgress = true
                    fragmentCommentsBinding.listPhotos.visibility = View.GONE
                    fragmentCommentsBinding.progressBarPhotos.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    fragmentCommentsBinding.progressBarPhotos.visibility = View.GONE
                    fragmentCommentsBinding.listPhotos.visibility = View.VISIBLE
                    setupPostsAdapter(it.data)
                }

                is Result.Failure -> {
                    fragmentCommentsBinding.progressBarPhotos.visibility = View.GONE
                    fragmentCommentsBinding.listPhotos.visibility = View.GONE
                }
            }
        }
    }

    private fun setupPostsAdapter(allValues: ArrayList<PhotosByPostUI>) {
        if (allValues.isNotEmpty()) {
            fragmentCommentsBinding.listPhotos.setHasFixedSize(true)
            fragmentCommentsBinding.listPhotos.layoutManager =
                GridLayoutManager(requireContext(), 2)
            photosRecyclerViewAdapter = PhotosRecyclerViewAdapter(
                allValues
            )
            fragmentCommentsBinding.listPhotos.adapter = photosRecyclerViewAdapter
            photosRecyclerViewAdapter.submitList(allValues)
        } else {
            fragmentCommentsBinding.listPhotos.visibility = View.GONE
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
    }
}