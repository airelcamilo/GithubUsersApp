package com.airelcamilo.githubusersapp.di

import com.airelcamilo.core.domain.usecase.UserInteractor
import com.airelcamilo.core.domain.usecase.UserUseCase
import com.airelcamilo.githubusersapp.detail.DetailViewModel
import com.airelcamilo.githubusersapp.detail.ListUserViewModel
import com.airelcamilo.githubusersapp.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ListUserViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}