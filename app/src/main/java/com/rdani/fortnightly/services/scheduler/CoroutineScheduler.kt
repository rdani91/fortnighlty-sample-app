package com.rdani.fortnightly.services.scheduler

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineScheduler {

    fun background(): CoroutineDispatcher

}