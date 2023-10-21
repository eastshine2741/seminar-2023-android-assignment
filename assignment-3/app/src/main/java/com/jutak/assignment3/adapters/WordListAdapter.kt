package com.jutak.assignment3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutak.assignment3.data.vo.BriefWordListVO
import com.jutak.assignment3.databinding.WordlistItemBinding

class WordListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSet: List<BriefWordListVO> = emptyList()

    class WordListViewHolder(private val binding: WordlistItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(creator: String, title: String) {
            binding.wordlistCreator.text = creator
            binding.wordlistTitle.text = title
        }
    }

    fun submitList(dataSet: List<BriefWordListVO>) {
        this.dataSet = dataSet
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WordListViewHolder(WordlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WordListViewHolder).bind(dataSet[position].owner, dataSet[position].name)
    }
}