package com.vychev.keyvaluestore.domain.entity

class Snapshot(val entries: Iterable<SnapshotEntry<String, String>>)

data class SnapshotEntry<T, V>(val key: T, val value: V)