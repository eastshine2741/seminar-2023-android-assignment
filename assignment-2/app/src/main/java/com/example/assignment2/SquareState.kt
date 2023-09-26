package com.example.assignment2

enum class SquareState {
    EMPTY,
    O,
    X;

    fun reversed(): SquareState =
        when (this) {
            O -> X
            X -> O
            EMPTY -> EMPTY
        }

    override fun toString(): String {
        return when (this) {
            O -> "O"
            X -> "X"
            EMPTY -> ""
        }
    }
}