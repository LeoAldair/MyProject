package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myproject.database.Product
import com.example.myproject.databinding.FragmentProductsDetails2Binding
import com.example.myproject.remote.ProductEntry
import com.example.myproject.remote.RetrofitBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response


class FragmentProductsDetails2 : Fragment() {
    private lateinit var binding: FragmentProductsDetails2Binding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsDetails2Binding.inflate(layoutInflater)
        var accion = true
        val id = arguments?.getString("ID")
        val retrofit = RetrofitBuilder.create().getProductById(id.toString())
        val navController = findNavController()
        retrofit.enqueue(object : retrofit2.Callback<ProductEntry> {
            override fun onResponse(
                call: Call<ProductEntry>,
                response: Response<ProductEntry>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    binding.tvProductName.setText(responseBody?.name.toString())
                    binding.tvProductPrice.setText("$ ${responseBody?.price.toString()} MXN")
                    binding.tvProductCategory.setText(responseBody?.category.toString())
                    binding.tvProductDescription.setText(responseBody?.description.toString())
                    binding.tvProductRate.setText(responseBody?.rating?.rate.toString())
                    binding.tvProductCount.setText("(${responseBody?.rating?.count.toString()} opiniones)")
                    val image = binding.ivProduct
                    Picasso.get().load(responseBody?.urlImage.toString()).into(image)
                    mainViewModel.getProducts()
                    mainViewModel.savedProducts.observe(viewLifecycleOwner) { productList ->
                        if (!productList.isNullOrEmpty()) {
                            for (product in productList) {
                                if(product.product_id == id && product.product_user_id == "user_${getString(R.string.user)}"){
                                    binding.btnInsertProduct.setBackgroundDrawable(resources.getDrawable(R.drawable.remove_icon))
                                    accion = false
                                    break
                                }else{
                                    accion = true
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ProductEntry>, t: Throwable) {
                Log.d("retrofitResponse", "error: ${t.message}")
            }
        })

        binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_fragmentProductsDetails2_to_suggestionFragment)
        }

        binding.btnInsertProduct.setOnClickListener {
            if (accion == true){
                val id = arguments?.getString("ID")
                val retrofit = RetrofitBuilder.create().getProductById(id.toString())
                retrofit.enqueue(object : retrofit2.Callback<ProductEntry> {
                    override fun onResponse(
                        call: Call<ProductEntry>,
                        response: Response<ProductEntry>
                    ) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            mainViewModel.saveProduct(
                                Product(
                                    responseBody?.id.toString(),
                                    responseBody?.name.toString(),
                                    responseBody?.price.toString(),
                                    responseBody?.rating?.rate.toString(),
                                    responseBody?.category.toString(),
                                    responseBody?.urlImage.toString(),
                                    "user_${getString(R.string.user)}",
                                )
                            )
                            binding.btnInsertProduct.setBackgroundDrawable(resources.getDrawable(R.drawable.remove_icon))
                            accion = false
                        }
                    }
                    override fun onFailure(call: Call<ProductEntry>, t: Throwable) {
                        Log.d("retrofitResponse", "error: ${t.message}")
                    }
                })
            }else{
                val id = arguments?.getString("ID")
                val retrofit = RetrofitBuilder.create().getProductById(id.toString())
                retrofit.enqueue(object : retrofit2.Callback<ProductEntry> {
                    override fun onResponse(
                        call: Call<ProductEntry>,
                        response: Response<ProductEntry>
                    ) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            mainViewModel.deleteProduct(
                                Product(
                                    responseBody?.id.toString(),
                                    responseBody?.name.toString(),
                                    responseBody?.price.toString(),
                                    responseBody?.rating?.rate.toString(),
                                    responseBody?.category.toString(),
                                    responseBody?.urlImage.toString(),
                                    "user_${getString(R.string.user)}",
                                )
                            )
                            binding.btnInsertProduct.setBackgroundDrawable(resources.getDrawable(R.drawable.insert_icon))
                            accion = true
                        }
                    }
                    override fun onFailure(call: Call<ProductEntry>, t: Throwable) {
                        Log.d("retrofitResponse", "error: ${t.message}")
                    }
                })
            }
        }

        return binding.root
    }
}


