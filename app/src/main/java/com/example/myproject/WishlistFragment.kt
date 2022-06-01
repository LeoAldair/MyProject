package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myproject.callbacks.ProductDeleteCallback
import com.example.myproject.database.Product
import com.example.myproject.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment(), ProductDeleteCallback {
    private lateinit var binding: FragmentWishlistBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(layoutInflater)
        mainViewModel.getProducts()
        mainViewModel.savedProducts.observe(viewLifecycleOwner) { allProducts ->
            if (!allProducts.isNullOrEmpty()) {
                val productList: MutableList<Product> = mutableListOf()
                for (product in allProducts){
                    if (product.product_user_id == "user_${getString(R.string.user)}"){
                        productList.add(product)
                    }
                    var adapter = DeleteAdapter(productList, this@WishlistFragment)
                    binding.rvProductEntries.adapter = adapter

                }

            } else {
                Log.d("thesearetheusers", "null or empty")
            }
        }
        return binding.root
    }

    override fun onClick(product: Product) {
        mainViewModel.deleteProduct(
            Product(
                product.product_id,
                product.product_name,
                product.product_price,
                product.product_rate,
                product.product_category,
                product.product_url,
                "user_${getString(R.string.user)}",
            )
        )
        val direcction = WishlistFragmentDirections.actionWishlistFragmentSelf()
        findNavController().navigate(direcction)
    }


}