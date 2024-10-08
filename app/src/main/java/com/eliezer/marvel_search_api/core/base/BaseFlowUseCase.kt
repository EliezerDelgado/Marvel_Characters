package com.eliezer.marvel_search_api.core.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseFlowUseCase <in P, R>(private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<R> {
        return execute(parameters).flowOn(dispatcher)
    }

    protected abstract fun execute(params: P): Flow<R>
}