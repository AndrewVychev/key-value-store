package com.vychev.keyvaluestore.domain.entity

data class BeginTransactionParam(val isRoot: Boolean): CommandParams()