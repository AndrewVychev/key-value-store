package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.KeyValueParams
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository
import javax.inject.Inject

class SetValueCommandExecutor @Inject constructor(
    private val keyValueRepository: KeyValueRepository
) : CommandExecutor<KeyValueParams, Unit> {

    override suspend fun invoke(params: KeyValueParams): Result<Unit> {
        return Result.success(keyValueRepository.put(params.key, params.value))
    }
}