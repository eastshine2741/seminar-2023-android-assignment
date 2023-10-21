package com.jutak.assignment3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutak.assignment3.data.vo.WordVO
import com.jutak.assignment3.databinding.WordItemBinding

class WordAdapter(
    private val onItemClick: (WordVO) -> Unit,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSet: List<WordVO> = emptyList()

    class WordViewHolder(private val binding: WordItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WordVO, onItemClick: (WordVO) -> Unit) {
            binding.spell.text = item.spell
            binding.meaning.text = item.meaning
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    fun submitList(dataSet: List<WordVO>) {
        this.dataSet = dataSet
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WordViewHolder(WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WordViewHolder).bind(dataSet[position], onItemClick)
    }
}