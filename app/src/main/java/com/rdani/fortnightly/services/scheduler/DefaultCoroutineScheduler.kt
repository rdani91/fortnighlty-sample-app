package com.rdani.fortnightly.services.scheduler

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultCoroutineScheduler : CoroutineScheduler {

    override fun background(): CoroutineDispatcher = Dispatchers.IO
}