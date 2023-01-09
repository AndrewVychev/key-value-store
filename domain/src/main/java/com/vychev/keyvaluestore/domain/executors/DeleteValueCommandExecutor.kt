package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.KeyValueParams
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository

class DeleteValueCommandExecutor(
    private val keyValueRepository: KeyValueRepository,
) : CommandExecutor<KeyValueParams, Unit> {

    override suspend operator fun invoke(params: KeyValueParams): Result<Unit> {
        return Result.success(keyValueRepository.delete(params.key))
    }
}