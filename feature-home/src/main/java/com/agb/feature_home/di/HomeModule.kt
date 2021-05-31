package com.agb.feature_home.di

import com.agb.core.di.remote
import com.agb.feature_home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get(remote), get(remote)) }
}
