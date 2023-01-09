package com.vychev.keyvaluestore.domain.usecase

import com.vychev.keyvaluestore.domain.entity.Transaction
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import javax.inject.Inject

class GetCurrentTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    operator fun invoke(): Transaction {
        return transactionsRepository.getTop()
    }
}