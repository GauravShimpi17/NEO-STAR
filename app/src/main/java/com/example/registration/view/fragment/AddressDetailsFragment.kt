package com.example.registration.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.registration.R
import com.example.registration.data.model.AddressData
import com.example.registration.data.model.PrimaryData
import com.example.registration.databinding.FragmentAddressDetailsBinding
import com.example.registration.view.util.enumClass.Designation
import com.example.registration.view.util.enumClass.Education
import com.example.registration.view.util.enumClass.State
import com.example.registration.view.util.populateSpinner
import com.example.registration.viewModel.UserViewModel


class AddressDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAddressDetailsBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_address_details, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpToolbar()
        populateSpinner()
        setupObservers()
        setOnClickListeners()

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.primaryData = viewModel

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
                    .replace(R.id.frameRegistration, ExperienceDetailsFragment())
                    .addToBackStack(null).commit()
            }
        }

    }

    private fun setupObservers() {
        viewModel.addressDataError.observe(viewLifecycleOwner) { error ->
            if (error == null) return@observe
            binding.txtAddress.error = error.addressError
            binding.txtLandmark.error = error.landmarkError
            binding.txtCity.error = error.cityNameError
            binding.txtPinCode.error = error.pinCodeError
            if (error.stateError != null) {
                binding.errorTextView.text = error.stateError
            } else {
                binding.errorTextView.text = "Select State"
            }
        }
    }

    private fun setOnClickListeners() {

        binding.btnSubmit.setOnClickListener {

            val addressDataSet = AddressData(
                address = binding.txtAddress.text.toString(),
                landmark = binding.txtLandmark.text.toString(),
                city = binding.txtCity.text.toString(),
                state = binding.spnState.selectedItem.toString(),
                pinCode = binding.txtPinCode.text.toString()
            )

            if (viewModel.validateAddressData(addressDataSet)) {
                val user = viewModel.userPrimaryData.get() ?: return@setOnClickListener
                val professionalData =
                    viewModel.userProfessionalData.get() ?: return@setOnClickListener
                val addressData = viewModel.userAddressData.get() ?: return@setOnClickListener
                viewModel.saveUser(user, professionalData, addressData)
                requireActivity().finish()
            }
        }

    }

    private fun populateSpinner() {
        val spinner = binding.spnState
        val selectedItemState = State.getEducation(
            viewModel.userAddressData.get()?.state ?: State.NONE.toString()
        ).ordinal

        spinner.populateSpinner(
            requireContext(), State::class.java, selectedItemState
        ) {
            val oldData = viewModel.userAddressData.get() ?: return@populateSpinner
            val newData = oldData.copy(state = it.toString())
            viewModel.userAddressData.set(newData)

            Toast.makeText(requireContext(), "Selected: $it", Toast.LENGTH_SHORT).show()
        }


    }


}