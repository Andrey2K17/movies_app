package com.pg13.domain.entities

enum class OrderType(val type: String) {
    NUM_VOTE("NUM_VOTE"), DEFAULT(""), RATING("RATING"), EMPTY("")
}