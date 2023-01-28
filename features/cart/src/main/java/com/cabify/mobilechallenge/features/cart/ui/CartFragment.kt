package com.cabify.mobilechallenge.features.cart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cabify.library.utils.extensions.gone
import com.cabify.library.utils.extensions.visible
import com.cabify.mobilechallenge.core.base.ui.BaseFragment
import com.cabify.mobilechallenge.features.cart.databinding.FragmentCartBinding
import com.cabify.mobilechallenge.features.cart.presentation.viewmodel.CartViewModel
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Error
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Loading
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Success
import com.cabify.mobilechallenge.features.cart.ui.adapter.OrderPresentationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by viewModel()

    private val productsAdapter: OrderPresentationAdapter by lazy {
        OrderPresentationAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeViewState()
    }

    private fun setRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
        }
    }


    private fun observeViewState() {
        cartViewModel.viewState.observe(viewLifecycleOwner) { viewState ->
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
            binding.emptyCartView.visible()
            binding.recyclerView.gone()
        } else {
            binding.emptyCartView.gone()
            binding.recyclerView.visible()
            productsAdapter.submitList(viewState.orderPresentations)
        }
    }

    private fun setLoadingViewState() {
        binding.progressCircular.visible()
        binding.emptyCartView.gone()
        binding.recyclerView.gone()
    }

    private fun setErrorViewState() {
        binding.progressCircular.gone()
        binding.emptyCartView.gone()
        binding.recyclerView.gone()
        showErrorMessage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}