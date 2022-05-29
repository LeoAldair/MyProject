package com.example.myproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.databinding.ItemProductBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainAdapter (private val products: Array<JSONObject>): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding,mlistener)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainHolder, position: Int) {
        holder.render(products[position])
    }

    override fun getItemCount(): Int = products.size

    class MainHolder(val binding: ItemProductBinding, listener: onItemClickListener): RecyclerView.ViewHolder(binding.root){
        fun render(rest: JSONObject){
            binding.tvProductName.setText(rest.getString("name"))
            binding.tvProductCategory.setText(rest.getString("category"))
            binding.tvProductPrice.setText(rest.getString("price"))
            binding.tvProductRate.setText(rest.getString("rate"))
            val image = binding.ivProduct
            val url = rest.getString("urlImage")
            if (url != null && !url.isEmpty()){
                Picasso.get().load(rest.getString("urlImage").toString()).into(image)
            }else{

            }

        }
        init{
            binding.btnInfoProduct.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}