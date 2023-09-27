package com.example.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.databinding.HistoryListFirstItemBinding
import com.example.assignment2.databinding.HistoryListItemBinding

class HistoryListAdapter(
    private val dataSet: List<GameState>,
    val onReturnClicked: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val boardGrid: GridLayout
        private val returnButton: TextView
        private val turnText: TextView

        init {
            boardGrid = view.findViewById(R.id.board_grid_small)
            returnButton = view.findViewById(R.id.return_button)
            turnText = view.findViewById(R.id.turn_text)
        }

        fun bind(data: GameState) {
            boardGrid.forEachIndexed { idx, view ->
                (view as TextView).text = data.board[idx / 3][idx % 3].toString()
            }
            turnText.text = turnText.context.getString(R.string.turn_text, data.turn)
            returnButton.setOnClickListener {
                onReturnClicked(data.turn)
            }
        }
    }

    inner class FirstItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val returnButton: TextView

        init {
            returnButton = view.findViewById(R.id.return_button)
        }

        fun bind(data: GameState) {
            returnButton.setOnClickListener {
                onReturnClicked(data.turn)
            }
        }
    }

    enum class ItemType {
        FIRST,
        ORDINARY
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.FIRST.ordinal -> {
                FirstItemViewHolder(HistoryListFirstItemBinding.inflate(LayoutInflater.from(parent.context)).root)
            }

            else -> {
                ItemViewHolder(HistoryListItemBinding.inflate(LayoutInflater.from(parent.context)).root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataSet[position]
        if (item.isBoardEmpty()) {
            (holder as FirstItemViewHolder).bind(item)
        } else {
            (holder as ItemViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].isBoardEmpty()) ItemType.FIRST.ordinal else ItemType.ORDINARY.ordinal
    }
}