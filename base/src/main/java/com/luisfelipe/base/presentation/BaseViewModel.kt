package com.luisfelipe.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfelipe.core.result.ResultStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T : Any>(private val coroutineDispatcher: CoroutineDispatcher) :
    ViewModel() {

    private val _responseLiveData: MutableLiveData<ResultStatus<T>> by lazy {
        MutableLiveData<ResultStatus<T>>()
    }
    val responseLiveData: LiveData<ResultStatus<T>> by lazy { _responseLiveData }

    private var isLoading = false

    fun ViewModel.launch(
        dispatcher: CoroutineContext = coroutineDispatcher,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(dispatcher, block = block)

    private fun handleRequest(resource: ResultStatus<T>) {
        when (resource) {
            is ResultStatus.Success -> {
                isLoading = false
                _responseLiveData.postValue(resource)
            }
            is ResultStatus.Error -> {
                isLoading = false
                _responseLiveData.postValue(resource)
            }
            is ResultStatus.Loading -> {
                isLoading = true
                _responseLiveData.postValue(resource)
            }
        }
    }

}