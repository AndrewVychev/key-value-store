package com.vychev.keyvaluestore.domain.entity

import java.util.UUID

data class Transaction(val id:String = UUID.randomUUID().toString(), val isRoot: Boolean = false)

