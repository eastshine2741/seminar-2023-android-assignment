package com.example.assignment2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GameViewModel: ViewModel() {

    private val _gameHistory = MutableStateFlow(listOf(GameState()))
    val gameHistory: StateFlow<List<GameState>>
        get() = _gameHistory
    val currentState = _gameHistory.map {
        it.last()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, GameState())

    suspend fun markSquare(col: Int, row: Int) {
        if (currentState.value.board[col][row] == SquareState.EMPTY && currentState.value.summary == GameState.StateSummary.PLAYING) {
            moveToNextState(col, row, currentState.value.player)
        }
    }

    private suspend fun moveToNextState(col: Int, row: Int, player: SquareState) {
        val board = currentState.value.board.clone2d()
        board[col][row] = player

        val summary = if (isWin(board, player)) {
            if (player == SquareState.O) {
                GameState.StateSummary.O_WIN
            } else {
                GameState.StateSummary.X_WIN
            }
        } else if (isDraw(board)) {
            GameState.StateSummary.DRAW
        } else {
            GameState.StateSummary.PLAYING
        }

        _gameHistory.emit(
            _gameHistory.value.toMutableList().apply {
                add(currentState.value.copy(board = board, summary = summary, player = player.reversed(), turn = currentState.value.turn+1))
            }
        )
    }

    private fun isWin(board: Array<Array<SquareState>>, player: SquareState): Boolean {
        return board.all { row ->
            row.all {
                it == player
            }
        } ||
        board.indices.map { colIdx -> board.map { it[colIdx] } }.all { col ->
            col.all {
                it == player
            }
        } ||
        board.indices.map { colIdx -> board[colIdx][colIdx] }.all {
            it == player
        } ||
        board.indices.map { colIdx -> board[colIdx][board.size - colIdx - 1] }.all {
            it == player
        }
    }

    private fun isDraw(board: Array<Array<SquareState>>): Boolean {
        return board.flatten().all { it != SquareState.EMPTY}
    }
}