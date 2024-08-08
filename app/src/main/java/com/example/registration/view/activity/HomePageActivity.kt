package com.example.registration.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.data.database.AppDatabase
import com.example.registration.data.repo.LocalUserRepo
import com.example.registration.databinding.ActivityHomePageBinding
import com.example.registration.view.adapter.GetAllAdapter
import com.example.registration.viewModel.UserViewModel
import com.example.registration.viewModel.viewModelFactory.UserViewModelFactory

class HomePageActivity : AppCompatActivity() {

    private val binding: ActivityHomePageBinding by lazy {
        ActivityHomePageBinding.inflate(layoutInflater)
    }

    lateinit var viewModel: UserViewModel
    private lateinit var adapter: GetAllAdapter

    companion object{
        private const val TAG = "HomePage"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
        setUpRecycler()
        setUpViewModel()
        setUpObserver()

    }

    private fun setClickListener() {
        binding.fabAddButton.setOnClickListener {
            Intent(this, RegistrationActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun setUpRecycler() {
        adapter = GetAllAdapter()
        binding.rvAllList.adapter = adapter
        binding.rvAllList.layoutManager = LinearLayoutManager(this)
    }
    private fun setUpViewModel() {
        val repo = LocalUserRepo(AppDatabase.getDatabase(this))
        viewModel = ViewModelProvider(this, UserViewModelFactory(repo))[UserViewModel::class.java]
    }

    private fun setUpObserver() {
        viewModel.getAllUser().observe(this) { data ->
            adapter.dataList = data
        }
    }
}