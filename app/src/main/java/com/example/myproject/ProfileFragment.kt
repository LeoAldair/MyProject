package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myproject.databinding.FragmentProfileBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONArray


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val navController = findNavController()
        val user_id = getString(R.string.user)
        val database = Firebase.database
        val myRef = database.reference
        myRef.child("Users").child(user_id).child("name").get().addOnSuccessListener { userName ->
            binding.tvUserFirstNameData.setText(userName.value.toString())
        }
        myRef.child("Users").child(user_id).child("last").get().addOnSuccessListener { userLast ->
            binding.tvUserLastNameData.setText(userLast.value.toString())
        }
        myRef.child("Users").child(user_id).child("email").get().addOnSuccessListener { userEmail ->
            binding.tvUserEmailData.setText(userEmail.value.toString())
        }
        myRef.child("Users").child(user_id).child("level").get().addOnSuccessListener { userLevel ->
            val level = userLevel.value.toString().toInt()
            if(level<100) {
                binding.tvUserLevelData.setText("Nivel 1")
                binding.pgUserLevelData.progress = level
                val faltantes = (100 - level)
                binding.tvUserLevelData2.setText("${faltantes} puntos para siguiente nivel.")
            }else{
                if(level<200){
                    binding.tvUserLevelData.setText("Nivel 2")
                    binding.pgUserLevelData.progress = (level-100)
                    val faltantes = (200 - level)
                    binding.tvUserLevelData2.setText("${faltantes} puntos para siguiente nivel.")
                }else{
                    if(level<300){
                        binding.tvUserLevelData.setText("Nivel 3")
                        binding.pgUserLevelData.progress = (level-200)
                        val faltantes = (300 - level)
                        binding.tvUserLevelData2.setText("${faltantes} puntos para siguiente nivel.")
                    }else{
                        if(level<400){
                            binding.tvUserLevelData.setText("Nivel 4")
                            binding.pgUserLevelData.progress = (level-300)
                            val faltantes = (400 - level)
                            binding.tvUserLevelData2.setText("${faltantes} puntos para siguiente nivel.")
                        }else{
                            binding.tvUserLevelData.setText("Nivel 5")
                            binding.pgUserLevelData.progress = (level-400)
                            val faltantes = (500 - level)
                            binding.tvUserLevelData2.setText("${faltantes} puntos para ser Dios.")
                        }
                    }
                }
            }
        }
        mainViewModel.getProducts()
        mainViewModel.savedProducts.observe(viewLifecycleOwner) { productList ->
            if (!productList.isNullOrEmpty()) {
                var cantidad = 0
                for (product in productList) {
                    if(product.product_user_id == "user_${getString(R.string.user)}"){
                        cantidad += 1
                    }
                }
                binding.tvUserQuantityData.setText("Hay ${cantidad.toString()} articulos")
            } else {
                Log.d("thesearetheusers", "null or empty")
            }
        }

        binding.btnUpdateUser.setOnClickListener {
            /*val database = Firebase.database
            val myRef = database.reference
            myRef.child("Users").child("1").setValue(User("Leonardo Aldair","Resendiz Pantoja","leo@gmail.com","80" ))
            myRef.child("Users").child("2").setValue(User("Paulina","Ruiz Reyes","paupau@gmail.com","150" ))
            myRef.child("Users").child("3").setValue(User("Mauricio","Nieto","mauN@gmail.com","280" ))*/
            navController.navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }
        return binding.root
    }

}