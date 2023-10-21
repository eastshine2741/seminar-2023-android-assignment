package com.jutak.assignment3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jutak.assignment3.adapters.WordAdapter
import com.jutak.assignment3.databinding.ActivityDetailBinding
import com.jutak.assignment3.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    @Inject
    lateinit var viewModelAssistedFactory: DetailViewModel.AssistedFactoryWithId
    private val viewModel: DetailViewModel by viewModels<DetailViewModel> {
        DetailViewModel.provideFactory(viewModelAssistedFactory, intent.getIntExtra("wordListId", 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wordRecyclerView.adapter = WordAdapter()

        viewModel.wordListDetail.observe(this) {
            (binding.wordRecyclerView.adapter as WordAdapter).run {
                submitList(it.wordList)
                notifyDataSetChanged()
            }
            binding.appbarTitle.text = it.name
        }
    }
}