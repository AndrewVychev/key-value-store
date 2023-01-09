package com.vychev.keyvaluestore.domain.entity

sealed class Command(val id: String) {

    class BeginTransaction : Command("BEGIN")

    class CommitTransaction : Command("COMMIT")

    class RollbackTransaction : Command("ROLLBACK")

    class Count(val key: String) : Command("COUNT")

    class Delete(val key: String): Command("DELETE")

    class Set(val key: String, val value: String): Command("SET")

    class Get(val key: String): Command("GET")

}