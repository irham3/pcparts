package org.irham3.pcparts.di

import org.irham3.core.domain.usecase.ComponentInteractor
import org.irham3.core.domain.usecase.ComponentUseCase
import org.irham3.pcparts.detail.DetailComponentViewModel
import org.irham3.pcparts.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<ComponentUseCase> { ComponentInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailComponentViewModel(get()) }
}