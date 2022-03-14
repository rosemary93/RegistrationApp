package com.example.registrationapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.registrationapp.databinding.FragmentRegistrationBinding
import com.google.gson.Gson

const val SHARED_PREF_NAME = "data in sharedPref"

class RegistrationFragment : Fragment() {
    lateinit var binding: FragmentRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val sharedPref =
            activity?.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        if (sharedPref != null) {
            if (sharedPref.contains("person${index - 1}")) {
                Toast.makeText(activity, "shared pref not empty ", Toast.LENGTH_SHORT).show()
                // Log.d("sssss", "shared pref not empty ")
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            if (areValidInputs()) {
                val gender = if (binding.radioButtonMale.isChecked) {
                    "male"
                } else {
                    "female"
                }
                /*val dataBundle = bundleOf(
                    "name" to binding.editTextName.text.toString(),
                    "username" to binding.editTextUsername.text.toString(),
                    "email" to binding.editTextEmailAddress.text.toString(),
                    "password" to binding.editTextPassword.text.toString(),
                    "genderIsMAle" to isMale
                )*/
                val personInf = PersonInfo(
                    binding.editTextName.text.toString(),
                    binding.editTextUsername.text.toString(),
                    binding.editTextEmailAddress.text.toString(),
                    binding.editTextPassword.text.toString(), gender
                )
                val gson=Gson()
                val jsonString=gson.toJson(personInf)
                val action =
                    RegistrationFragmentDirections.actionRegistrationFragmentToShowInfoFragment(jsonString)
                findNavController().navigate(action)
            }
        }

    }


    private fun areValidInputs(): Boolean {
        if (binding.editTextName.text.isNullOrBlank()) {
            binding.editTextName.error = "please fill this field"
            return false
        }
        if (binding.editTextName.text.length < 4) {
            binding.editTextName.error = "Name is too short"
            return false
        }
        if (binding.editTextUsername.text.isNullOrBlank()) {
            binding.editTextUsername.error = "please fill this field"
            return false
        }
        if (binding.editTextEmailAddress.text.isNullOrBlank()) {
            binding.editTextEmailAddress.error = "please fill this field"
            return false
        }
        if (!binding.editTextEmailAddress.text.matches(Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"))) {
            binding.editTextEmailAddress.error = "invalid email address"
            return false
        }
        if (binding.editTextPassword.text.isNullOrBlank()) {
            binding.editTextPassword.error = "please fill this field"
            return false
        }
        if (binding.editTextRetypedPassword.text.isNullOrBlank()) {
            binding.editTextRetypedPassword.error = "please fill this field"
            return false
        }
        if (binding.editTextRetypedPassword.text.toString() != binding.editTextPassword.text.toString()) {
            binding.editTextRetypedPassword.error = "retyped password doesn't match password"
            return false
        }

        if (binding.radioButtonFemale.isChecked == binding.radioButtonMale.isChecked) {
            Toast.makeText(activity, "please choose a gender", Toast.LENGTH_SHORT).show()
            return false
        }
        return true

    }

}