package org.irham3.pcparts.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.irham3.core.domain.usecase.ComponentUseCase

class MainViewModel (componentUseCase: ComponentUseCase) : ViewModel() {

    val component = componentUseCase.getAllComponent().asLiveData()
}