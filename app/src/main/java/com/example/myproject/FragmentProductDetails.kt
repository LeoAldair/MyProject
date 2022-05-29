package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myproject.databinding.FragmentProductDetailsBinding
import com.example.myproject.remote.ProductEntry
import com.example.myproject.remote.RetrofitBuilder
import com.squareup.picasso.Picasso
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class FragmentProductDetails : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
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
                }
            }

            override fun onFailure(call: Call<ProductEntry>, t: Throwable) {
                Log.d("retrofitResponse", "error: ${t.message}")
            }
        })
        binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_fragmentProductDetails_to_searchFragment)
        }
        return binding.root
    }

}