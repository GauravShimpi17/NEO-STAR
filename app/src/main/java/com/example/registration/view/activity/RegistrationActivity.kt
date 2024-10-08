package com.example.registration.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.registration.data.database.AppDatabase
import com.example.registration.databinding.ActivityRegistrationBinding
import com.example.registration.view.fragment.PrimaryDetailsFragment
import com.example.registration.data.repo.LocalUserRepo
import com.example.registration.viewModel.UserViewModel
import com.example.registration.viewModel.viewModelFactory.UserViewModelFactory

class RegistrationActivity : AppCompatActivity() {

    private lateinit var viewModel : UserViewModel

    private lateinit var binding: ActivityRegistrationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repo = LocalUserRepo(AppDatabase.getDatabase(this))
        viewModel = ViewModelProvider(this, UserViewModelFactory(repo))[UserViewModel::class.java]

        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameRegistration.id, PrimaryDetailsFragment())
//            replace(binding.frameRegistration.id, ExperienceDetails())
//            replace(binding.frameRegistration.id, AddressDetails())
        }.commit()
    }
}