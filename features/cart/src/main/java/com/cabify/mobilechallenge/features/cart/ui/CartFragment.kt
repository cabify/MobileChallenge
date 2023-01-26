package com.cabify.mobilechallenge.features.cart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cabify.mobilechallenge.core.base.ui.BaseFragment
import com.cabify.mobilechallenge.features.cart.databinding.FragmentCartBinding
import com.cabify.mobilechallenge.features.cart.presentation.viewmodel.CartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by viewModel()

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
        cartViewModel.viewState.observe(viewLifecycleOwner) {
            
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}