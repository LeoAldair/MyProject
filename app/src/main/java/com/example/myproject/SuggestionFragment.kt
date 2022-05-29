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
import com.example.myproject.remote.ProductEntry
import com.example.myproject.remote.RetrofitBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class SuggestionFragment : Fragment() {
    private lateinit var binding: FragmentSuggestionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuggestionBinding.inflate(layoutInflater)
        val ProductData: Array<JSONObject> = arrayOf(
            JSONObject("{\"name\": \"\", \"price\":\"\",\"category\":\"\",\"urlImage\":\"\",\"rate\":\"\"}"),
            JSONObject("{\"name\": \"\", \"price\":\"\",\"category\":\"\",\"urlImage\":\"\",\"rate\":\"\"}"),
            JSONObject("{\"name\": \"\", \"price\":\"\",\"category\":\"\",\"urlImage\":\"\",\"rate\":\"\"}"),
            JSONObject("{\"name\": \"\", \"price\":\"\",\"category\":\"\",\"urlImage\":\"\",\"rate\":\"\"}"),
            JSONObject("{\"name\": \"\", \"price\":\"\",\"category\":\"\",\"urlImage\":\"\",\"rate\":\"\"}"),
        )
        for (i in 0..4) {
            val numbers = (1..20)
            val randomNumber = numbers.random()
            val retrofit = RetrofitBuilder.create().getProductById(randomNumber.toString())
            retrofit.enqueue(object : retrofit2.Callback<ProductEntry> {
                override fun onResponse(
                    call: Call<ProductEntry>,
                    response: Response<ProductEntry>
                ) {
                    val responseBody = response.body()
                    ProductData.set(i, JSONObject("{\"name\": \"${responseBody?.name.toString()}\", \"price\":\"$ ${responseBody?.price.toString()} MXN\",\"category\":\"${responseBody?.category.toString()}\",\"urlImage\":\"${responseBody?.urlImage.toString()}\",\"rate\":\"${responseBody?.rating?.rate.toString()}\"}"))
                    var adapter = MainAdapter(ProductData)
                    binding.rvProductEntries.adapter = adapter
                    adapter.setOnItemClickListener(object : MainAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val direcction = SuggestionFragmentDirections.actionSuggestionFragmentToFragmentProductsDetails2(responseBody?.id.toString())
                            findNavController().navigate(direcction)
                        }

                    })
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