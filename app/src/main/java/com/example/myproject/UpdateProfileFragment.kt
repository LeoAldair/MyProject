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
        val user_id = getString(R.string.user)
        var nivel = 0
        val navController = findNavController()
        val database = Firebase.database
        val myRef = database.reference
        myRef.child("Users").child(user_id).child("name").get().addOnSuccessListener { userName ->
            binding.etUserFirstNameData.setText(userName.value.toString())
        }
        myRef.child("Users").child(user_id).child("last").get().addOnSuccessListener { userLast ->
            binding.etUserLastNameData.setText(userLast.value.toString())
        }
        myRef.child("Users").child(user_id).child("email").get().addOnSuccessListener { userEmail ->
            binding.etUserEmailData.setText(userEmail.value.toString())
        }
        myRef.child("Users").child(user_id).child("level").get().addOnSuccessListener { userLevel ->
            nivel = userLevel.value.toString().toInt()
        }
        binding.btnUpdateUser.setOnClickListener {
            myRef.child("Users").child(getString(R.string.user)).setValue(User(binding.etUserFirstNameData.text.toString(),binding.etUserLastNameData.text.toString(),binding.etUserEmailData.text.toString(), nivel.toString()))
            navController.navigate(R.id.action_updateProfileFragment_to_profileFragment)
        }
        binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_updateProfileFragment_to_profileFragment)
        }
        return binding.root
    }

}