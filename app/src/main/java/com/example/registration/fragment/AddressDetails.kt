package com.example.registration.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.registration.R
import com.example.registration.databinding.FragmentAddressDetailsBinding
import com.example.registration.util.State
import com.example.registration.util.Util
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

        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        binding.primaryData = viewModel
        binding.btnSubmit.setOnClickListener {
            Log.d("hello", viewModel.userData.get().toString())
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
            val oldData = viewModel.userData.get() ?: return@populateSpinner
            val newData = oldData.copy(addressData = oldData.addressData.copy(state = state))
            viewModel.userData.set(newData)
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