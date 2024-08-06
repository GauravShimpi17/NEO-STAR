package com.example.registration.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.data.repo.UserRepo
import com.example.registration.viewModel.UserViewModel

class UserViewModelFactory(private val repo: UserRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repo) as T
    }
}