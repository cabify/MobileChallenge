package com.cabify.mobilechallenge.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cabify.library.utils.extensions.displayDialog
import com.cabify.library.utils.extensions.gone
import com.cabify.library.utils.extensions.visible
import com.cabify.mobilechallenge.core.base.ui.BaseFragment
import com.cabify.mobilechallenge.features.home.databinding.FragmentHomeBinding
import com.cabify.mobilechallenge.features.home.presentation.viewmodel.HomeViewModel
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Error
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Loading
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Success
import com.cabify.mobilechallenge.features.home.ui.adapter.ProductsAdapter
import com.cabify.mobilechallenge.shared.commonui.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.loadProductList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
        }
    }


    private fun observeViewState() {
        homeViewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is Success -> setSuccessViewState(viewState)
                is Error -> setErrorViewState()
                Loading -> setLoadingViewState()
            }
        }
    }

    private fun setSuccessViewState(viewState: Success) {
        binding.progressCircular.gone()
        if (viewState.isEmpty) {
            binding.noProductsView.visible()
            binding.recyclerView.gone()
        } else {
            binding.noProductsView.gone()
            binding.recyclerView.visible()
            productsAdapter.submitList(viewState.productPresentation.toMutableList())
        }
    }

    private fun setLoadingViewState() {
        binding.progressCircular.visible()
        binding.noProductsView.gone()
        binding.recyclerView.gone()
    }

    private fun setErrorViewState() {
        binding.progressCircular.gone()
        binding.noProductsView.gone()
        binding.recyclerView.gone()
        requireContext().displayDialog(
            titleRes = R.string.general_error_title_message,
            contentRes = R.string.general_error_content_message,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}