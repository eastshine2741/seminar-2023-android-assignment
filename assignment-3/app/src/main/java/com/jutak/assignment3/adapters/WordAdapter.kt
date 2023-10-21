package com.jutak.assignment3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutak.assignment3.data.vo.WordVO
import com.jutak.assignment3.databinding.WordItemBinding

class WordAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSet: List<WordVO> = emptyList()

    class WordViewHolder(private val binding: WordItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WordVO) {
            binding.spell.text = item.spell
            binding.meaning.text = item.meaning
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
        (holder as WordViewHolder).bind(dataSet[position])
    }
}