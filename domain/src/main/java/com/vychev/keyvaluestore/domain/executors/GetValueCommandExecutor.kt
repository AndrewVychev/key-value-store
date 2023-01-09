package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.KeyValueParams
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository

class GetValueCommandExecutor(
    private val keyValueRepository: KeyValueRepository
) : CommandExecutor<KeyValueParams, String> {

    override suspend operator fun invoke(params: KeyValueParams): Result<String> {
        val result = keyValueRepository.get(params.key)
        return if (result == null) {
            Result.failure(NullPointerException())
        } else {
            Result.success(result)
        }
    }
}