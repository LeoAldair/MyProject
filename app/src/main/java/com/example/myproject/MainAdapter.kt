package com.example.myproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.callbacks.ProductListCallback
import com.example.myproject.database.Product
import com.example.myproject.databinding.ItemProductBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainAdapter(private val products: Array<JSONObject>, private val callBack: ProductListCallback): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding,callBack)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainHolder, position: Int) {
        holder.render(products[position])
    }

    override fun getItemCount(): Int = products.size

    class MainHolder(val binding: ItemProductBinding, val callBack: ProductListCallback): RecyclerView.ViewHolder(binding.root){
        fun render(rest: JSONObject){
            binding.tvProductName.setText(rest.getString("name"))
            binding.tvProductCategory.setText(rest.getString("category"))
            binding.tvProductPrice.setText(rest.getString("price"))
            binding.tvProductRate.setText(rest.getString("rate"))
            val image = binding.ivProduct
            val url = rest.getString("urlImage")
            if (url != null && !url.isEmpty()){
                Picasso.get().load(rest.getString("urlImage").toString()).into(image)
            }
            binding.btnInfoProduct.setOnClickListener {
                callBack.onClick(rest.getString("id"))
            }
        }

    }

}