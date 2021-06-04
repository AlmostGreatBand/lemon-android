package com.agb.feature_registration.di

import com.agb.feature_registration.ui.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    viewModel { RegistrationViewModel(get()) }
}
