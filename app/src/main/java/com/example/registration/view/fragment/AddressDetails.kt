package com.example.registration.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.registration.R
import com.example.registration.data.database.AppDatabase
import com.example.registration.data.repo.LocalUserRepo
import com.example.registration.databinding.FragmentAddressDetailsBinding
import com.example.registration.view.util.enumClass.State
import com.example.registration.view.util.Util
import com.example.registration.viewModel.UserViewModel


class AddressDetails : Fragment() {

    private lateinit var binding: FragmentAddressDetailsBinding

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_address_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        populateSpinner()

        val repo = LocalUserRepo(AppDatabase.getDatabase(requireContext()))
        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        binding.primaryData = viewModel
        binding.btnSubmit.setOnClickListener {
            Log.d("hello", viewModel.userPrimaryData.get().toString())
            Log.d("hello", viewModel.userProfessionalData.get().toString())
            Log.d("hello", viewModel.userAddressData.get().toString())
            val user = viewModel.userPrimaryData.get() ?: return@setOnClickListener
            val professionalData = viewModel.userProfessionalData.get() ?: return@setOnClickListener
            val addressData = viewModel.userAddressData.get() ?: return@setOnClickListener
            viewModel.saveUser(user, professionalData, addressData)
        }
    }


    private fun setUpToolbar() {

        val toolbar = binding.appbarAddress.toolbar
        binding.appbarAddress.tbTitle.text = getString(R.string.tb_title_address)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowTitleEnabled(false)
            }

            toolbar.setNavigationOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameRegistration, ExperienceDetails())
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    private fun populateSpinner() {
        val spinner = binding.spnState

        val states =
            State.entries.map { it.state }.toMutableList()

        Util.SpinnerUtils.populateSpinner(
            requireContext(),
            spinner,
            states,
            hint = "Select State"
        ) {
            var state = State.NONE
            if (it != null) {
                state = State.valueOf(it.uppercase().replace(" ", "_"))
            }
            val oldData = viewModel.userAddressData.get() ?: return@populateSpinner
            val newData = oldData.copy(state = state.toString())
            viewModel.userAddressData.set(newData)
        }

        /*val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            states
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.setSelection(0, false)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    // Handle case where hint item is selected
                    Toast.makeText(requireContext(), "Please select a state", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // Handle valid selection
                    val selectedState = State.entries.find { it.state == states[position] }
                    Toast.makeText(
                        requireContext(),
                        "Selected: ${selectedState?.state}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }*/
    }


}