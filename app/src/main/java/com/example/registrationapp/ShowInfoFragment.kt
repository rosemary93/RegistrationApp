package com.example.registrationapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.example.registrationapp.databinding.FragmentShowInfoBinding

const val SHARED_PREFERENCE_NAME="data in sharedPref"
var index=0
class ShowInfoFragment : Fragment() {
    private lateinit var binding: FragmentShowInfoBinding
    lateinit var personInfo:PersonInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            val jsonString=it!!.getString("personInfo")
            val gson=Gson()
            personInfo=gson.fromJson(jsonString,PersonInfo::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binding.buttonSave.setOnClickListener {
            setDataInSharedPref()
            findNavController().navigate(R.id.action_showInfoFragment_to_registrationFragment)
        }

    }
    private fun setDataInSharedPref()
    {
        val sharedPreference =
            activity?.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        val gson=Gson()
        val gsonString=gson.toJson(personInfo)
        editor?.putString("person$index",gsonString)
        index++
        editor?.apply()
    }

    private fun initViews(){
        binding.textViewName.text = personInfo.name
        binding.textViewUsername.text = personInfo.username
        binding.textViewEmail.text = personInfo.email
        binding.textViewPassword.text = personInfo.password
        binding.textViewGender.text=personInfo.gender

       /* personInfo= PersonInfo(requireArguments().getString("name", ""),
            requireArguments().getString("username", ""),
            requireArguments().getString("email", ""),
            requireArguments().getString("password", ""),"")*/
        /*val genderIsMale = requireArguments().getBoolean("genderIsMale")
        personInfo.gender=if (genderIsMale) {
            "male"
        } else {
            "female"
        }*/
    }

}