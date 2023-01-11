package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.KeyValueParams
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository
import javax.inject.Inject

class CountValueCommandExecutor @Inject constructor(
    private val keyValueRepository: KeyValueRepository
) : CommandExecutor<KeyValueParams, Int> {

    override suspend operator fun invoke(params: KeyValueParams): Result<Int> {
        return Result.success(keyValueRepository.count(params.key))
    }
}