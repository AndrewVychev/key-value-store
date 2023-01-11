package com.vychev.keyvaluestore.domain.usecase

import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import javax.inject.Inject

class AddCommandToTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
){

    operator fun invoke() {

    }
}