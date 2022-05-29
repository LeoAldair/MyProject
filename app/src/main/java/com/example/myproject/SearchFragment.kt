package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myproject.databinding.FragmentProfileBinding
import com.example.myproject.databinding.FragmentSearchBinding
import com.example.myproject.databinding.FragmentSuggestionBinding
import com.example.myproject.databinding.ItemProductBinding
import com.example.myproject.remote.ProductEntry
import com.example.myproject.remote.RetrofitBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.text.FieldPosition

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var binding2: ItemProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding2 = ItemProductBinding.inflate(layoutInflater)
        binding = FragmentSearchBinding.inflate(layoutInflater)
        val navController = findNavController()

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
                            JSONObject("{\"name\": \"${responseBody?.name.toString()}\", \"price\":\"$ ${responseBody?.price.toString()} MXN\",\"category\":\"${responseBody?.category.toString()}\",\"urlImage\":\"${responseBody?.urlImage.toString()}\",\"rate\":\"${responseBody?.rating?.rate.toString()}\"}")
                        )
                        var adapter = MainAdapter(ProductData)

                        binding.rvProductEntries.adapter = adapter
                        adapter.setOnItemClickListener(object : MainAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                val direcction = SearchFragmentDirections.actionSearchFragmentToFragmentProductDetails(binding.etSearchEntry.text.toString())
                                findNavController().navigate(direcction)
                            }

                        })
                    }
                }

                override fun onFailure(call: Call<ProductEntry>, t: Throwable) {
                    Log.d("retrofitResponse", "error: ${t.message}")
                }
            })
        }
        // Inflate the layout for this fragment
        return binding.root

    }

}