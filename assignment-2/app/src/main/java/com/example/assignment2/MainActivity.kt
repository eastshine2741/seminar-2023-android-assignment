package com.example.assignment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.forEachIndexed
import androidx.lifecycle.lifecycleScope
import com.example.assignment2.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardGrid.forEachIndexed { idx, view ->
            view.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.markSquare(idx / 3, idx % 3)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.currentState.collect {
                binding.boardGrid.forEachIndexed { idx, view ->
                    (view as TextView).text = it.board[idx / 3][idx % 3].toString()
                }
                binding.summaryText.text = when (it.summary) {
                    GameState.StateSummary.PLAYING -> getString(R.string.summary_playing_text, it.player.toString())
                    GameState.StateSummary.DRAW -> getString(R.string.summary_draw_text)
                    GameState.StateSummary.O_WIN -> getString(R.string.summary_o_win)
                    GameState.StateSummary.X_WIN -> getString(R.string.summary_x_win)
                }
            }
        }

        binding.drawerButton.setOnClickListener {
            binding.root.openDrawer(binding.drawer)
        }

        lifecycleScope.launch {
            viewModel.gameHistory.collect {
                val adapter = HistoryListAdapter(it) {
                    lifecycleScope.launch {
                        viewModel.moveBackToState(it)
                        binding.root.closeDrawer(binding.drawer)
                    }
                }
                binding.gameHistoryList.swapAdapter(adapter, false)
            }
        }
    }
}