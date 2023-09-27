package com.example.assignment2

data class GameState (
    val board: Array<Array<SquareState>> = Array(3) { Array(3) { SquareState.EMPTY } },
    val summary: StateSummary = StateSummary.PLAYING,
    val player: SquareState = SquareState.O,
    val turn: Int = 0
) {
    enum class StateSummary {
        PLAYING,
        DRAW,
        O_WIN,
        X_WIN,
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (!board.contentDeepEquals(other.board)) return false
        if (summary != other.summary) return false

        return true
    }

    override fun hashCode(): Int {
        var result = board.contentDeepHashCode()
        result = 31 * result + summary.hashCode()
        return result
    }

    fun isBoardEmpty(): Boolean =
        board.all { row ->
            row.all {
                it == SquareState.EMPTY
            }
        }
}