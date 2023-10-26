package com.jutak.assignment3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jutak.assignment3.adapters.WordAdapter
import com.jutak.assignment3.data.vo.WordVO
import com.jutak.assignment3.databinding.ActivityDetailBinding
import com.jutak.assignment3.databinding.DialogWordDetailBinding
import com.jutak.assignment3.network.launchSuspendApi
import com.jutak.assignment3.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    @Inject
    lateinit var viewModelAssistedFactory: DetailViewModel.AssistedFactoryWithId
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(viewModelAssistedFactory, intent.getIntExtra("wordListId", 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wordRecyclerView.adapter = WordAdapter(onItemClick = { word ->
            showWordDialog(word)
        })

        viewModel.wordListDetail.observe(this) {
            (binding.wordRecyclerView.adapter as WordAdapter).run {
                submitList(it.wordList)
                notifyDataSetChanged()
            }
            binding.appbarTitle.text = it.name
        }

        lifecycleScope.launch {
            launchSuspendApi(this@DetailActivity) {
                viewModel.getWordListDetail()
            }
        }
    }

    fun showWordDialog(item: WordVO) {
        val dialogBinding = DialogWordDetailBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialogBinding.run {
            spell.text = item.spell
            meaning.text = item.meaning
            synonym.text = item.synonym
            antonym.text = item.antonym
            sentence.text = item.sentence
            dismissButton.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}