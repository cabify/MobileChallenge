package com.cabify.challenge.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.databinding.FragmentStoreBinding
import com.cabify.challenge.presentation.adapter.ProductsAdapter
import com.cabify.challenge.presentation.views.StoreViewModel
import com.cabify.challenge.presentation.views.StoreViewModelFactory

class StoreFragment : Fragment() {


    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private val view = StoreViewModelFactory().create(StoreViewModel::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        view.start()
        observeEvents()
        return binding.root

    }

    private fun observeEvents() {
        binding.recyclerProducts.layoutManager = LinearLayoutManager(requireContext())
        view.products.observe(viewLifecycleOwner) {
            createAdapter(it)
        }
    }

    private fun createAdapter(it: Products) {
        binding.recyclerProducts.adapter = ProductsAdapter(
            it.getAllProducts()
        ) { product ->
            view.onAddToCart(product)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.buyButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}