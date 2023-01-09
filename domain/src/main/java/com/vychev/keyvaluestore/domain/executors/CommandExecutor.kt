package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.CommandParams

interface CommandExecutor<P: CommandParams, R> {

    suspend operator fun invoke(params: P): Result<R>

}

