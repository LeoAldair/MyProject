package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myproject.databinding.FragmentProfileBinding
import com.example.myproject.databinding.FragmentUpdateProfileBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject


class UpdateProfileFragment : Fragment() {
    private lateinit var binding:FragmentUpdateProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
        val usuario = "Leonardo"
        var wishlist = 0
        var nivel = 0
        val navController = findNavController()
        val database = Firebase.database
        val myRef = database.reference
        myRef.child("Users").get().addOnSuccessListener { response ->
            Log.d("json123",response.value.toString())
            val resmap = response.value as ArrayList<Map<String, Any>>
            resmap.forEach { user ->
                if(!user.isNullOrEmpty() && user["name"] == usuario){
                    binding.etUserFirstNameData.setText(user["name"].toString())
                    binding.etUserLastNameData.setText(user["last"].toString())
                    binding.etUserEmailData.setText(user["email"].toString())
                    wishlist = user["wishlist"].toString().toInt()
                    nivel = user["level"].toString().toInt()
                }
            }


        }.addOnFailureListener {
            Log.e("json123", "FATALError")
        }
        binding.btnUpdateUser.setOnClickListener {
            myRef.child("Users").child("1").setValue(User(binding.etUserFirstNameData.text.toString(),binding.etUserLastNameData.text.toString(),binding.etUserEmailData.text.toString(), wishlist, nivel))
            navController.navigate(R.id.action_updateProfileFragment_to_profileFragment)
        }
        binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_updateProfileFragment_to_profileFragment)
        }
        return binding.root
    }

}