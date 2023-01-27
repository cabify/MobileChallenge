package com.cabify.challenge.presentation.store.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.databinding.FragmentCartBinding
import com.cabify.challenge.presentation.store.adapter.CartAdapter
import com.cabify.challenge.presentation.store.views.StoreViewModel
import com.cabify.challenge.presentation.store.views.StoreViewModelFactory

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val view = StoreViewModelFactory().create(StoreViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        observeEvents()
        return binding.root

    }

    private fun observeEvents() {
        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        view.cart.observe(viewLifecycleOwner) {
            createAdapter(it)
        }
    }

    private fun createAdapter(it: Products) {
        binding.recyclerCart.adapter = CartAdapter(
            it.getAllProducts()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.view.getProductsFromCart()
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}