package com.example.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView

class HistoryListAdapter(
    private val dataSet: List<GameState>,
    val onReturnClicked: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
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

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataSet[position]
        (holder as ViewHolder).bind(item)
    }

}