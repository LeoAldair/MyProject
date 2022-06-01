package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myproject.callbacks.ProductListCallback
import com.example.myproject.databinding.FragmentSearchBinding
import com.example.myproject.remote.ProductEntry
import com.example.myproject.remote.RetrofitBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class SearchFragment : Fragment(), ProductListCallback {
    private lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.btnSearch.setOnClickListener {
            val id = binding.etSearchEntry.text.toString()
            val retrofit = RetrofitBuilder.create().getProductById(id)
            retrofit.enqueue(object : retrofit2.Callback<ProductEntry> {
                override fun onResponse(
                    call: Call<ProductEntry>,
                    response: Response<ProductEntry>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val ProductData: Array<JSONObject> = arrayOf(
                            JSONObject("{\"id\": \"${responseBody?.id}\",\"name\": \"${responseBody?.name.toString()}\", \"price\":\"$ ${responseBody?.price.toString()} MXN\",\"category\":\"${responseBody?.category.toString()}\",\"urlImage\":\"${responseBody?.urlImage.toString()}\",\"rate\":\"${responseBody?.rating?.rate.toString()}\"}")
                        )

                        var adapter = MainAdapter(ProductData, this@SearchFragment)
                        binding.rvProductEntries.adapter = adapter
                    }
                }
                override fun onFailure(call: Call<ProductEntry>, t: Throwable) {
                    Log.d("retrofitResponse", "error: ${t.message}")
                }
            })
        }
        return binding.root

    }

    override fun onClick(id: String) {
        val directions = SearchFragmentDirections.actionSearchFragmentToFragmentProductDetails(id)
        findNavController().navigate(directions)
    }

}