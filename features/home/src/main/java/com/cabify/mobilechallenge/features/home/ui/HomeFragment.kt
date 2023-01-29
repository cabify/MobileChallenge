package com.cabify.mobilechallenge.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cabify.library.utils.extensions.gone
import com.cabify.library.utils.extensions.showToast
import com.cabify.library.utils.extensions.visible
import com.cabify.mobilechallenge.core.base.ui.BaseFragment
import com.cabify.mobilechallenge.features.home.databinding.FragmentHomeBinding
import com.cabify.mobilechallenge.features.home.presentation.viewmodel.HomeViewModel
import com.cabify.mobilechallenge.features.home.presentation.viewstate.AddProductToCartSucceed
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Error
import com.cabify.mobilechallenge.features.home.presentation.viewstate.ErrorEvent
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Loading
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Success
import com.cabify.mobilechallenge.features.home.ui.adapter.ProductsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(::addProductToCart)
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
        setRecyclerView()
        observeViewState()
        observeViewEvents()
    }

    private fun setRecyclerView() {
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

    private fun observeViewEvents() {
        homeViewModel.viewEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                AddProductToCartSucceed -> showProductAddedToCartMessage()
                is ErrorEvent -> showErrorMessage()
            }
        }
    }

    private fun showProductAddedToCartMessage() {
        requireContext().showToast(com.cabify.mobilechallenge.shared.commonui.R.string.product_added_to_cart)
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
        showErrorMessage()
    }

    private fun addProductToCart(productId: String) {
        homeViewModel.addProductToCart(productId, quantity = ONE_QUANTITY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ONE_QUANTITY =
            1 //We didn't add the option of add various items at the same time
        //but the use case is perfectly working if we add more than one quantitiess
    }
}