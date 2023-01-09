package com.vychev.keyvaluestore.domain.usecase

import com.vychev.keyvaluestore.domain.entity.Transaction
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import javax.inject.Inject

class ClearCommandsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {

    operator fun invoke() {
        transactionsRepository.iterable().forEach {
            transactionsRepository.remove(it.id)
        }
        transactionsRepository.add(Transaction(isRoot = true))
    }
}