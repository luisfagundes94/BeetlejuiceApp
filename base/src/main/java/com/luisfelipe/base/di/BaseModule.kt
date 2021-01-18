package com.luisfelipe.base.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val baseModule = module {
    Dispatchers.IO
}