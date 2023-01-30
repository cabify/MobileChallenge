package com.cabify.challenge.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cabify.challenge.core.domain.cart.Cart
import com.cabify.challenge.databinding.FragmentCartBinding
import com.cabify.challenge.presentation.adapter.CartAdapter
import com.cabify.challenge.presentation.views.StoreViewModel
import com.cabify.challenge.presentation.views.StoreViewModelFactory

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


        binding.confirmButton.setOnClickListener {
            createConfirmPurchaseToast()
            view.onConfirmPurchase()
            findNavController().popBackStack()
        }

        view.cart.observe(viewLifecycleOwner) {
            confirmButtonIsEnabled(it)
            createAdapter(it)
            bindView(it)
        }

    }

    private fun createConfirmPurchaseToast() {
        Toast
            .makeText(requireContext(), "Your purchase will arrive soon", Toast.LENGTH_SHORT)
            .show()
    }

    private fun confirmButtonIsEnabled(it: Cart) {
        if (it.getProductsOrder().isEmpty()) {
            binding.confirmButton.isEnabled = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindView(cart: Cart) {
        binding.totalAmountNoPromo.text = "Total: ${cart.totalAmountNoPromo().presentPrice()}"
        binding.totalAmountPromo.text =
            "Total with discounts: ${cart.totalAmountPromo().presentPrice()}"
    }

    private fun createAdapter(it: Cart) {
        binding.recyclerCart.adapter = CartAdapter(
            it
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.view.onGetProductFromCart()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}