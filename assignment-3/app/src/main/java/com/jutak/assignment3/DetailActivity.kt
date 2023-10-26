package com.jutak.assignment3

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jutak.assignment3.adapters.WordAdapter
import com.jutak.assignment3.data.vo.WordVO
import com.jutak.assignment3.databinding.ActivityDetailBinding
import com.jutak.assignment3.databinding.DialogAddWordBinding
import com.jutak.assignment3.databinding.DialogDeleteWordlistBinding
import com.jutak.assignment3.databinding.DialogPasswordBinding
import com.jutak.assignment3.databinding.DialogWordDetailBinding
import com.jutak.assignment3.network.launchNetworkApi
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
            launchNetworkApi(this@DetailActivity) {
                viewModel.getWordListDetail()
            }
        }

        binding.editButton.setOnClickListener {
            showEditDialog()
        }

        viewModel.hasPermission.observe(this) { hasPermission ->
            if (hasPermission) {
                binding.editGroup.visibility = VISIBLE
                binding.editButton.visibility = GONE
            } else {
                binding.editGroup.visibility = GONE
                binding.editButton.visibility = VISIBLE
            }
        }

        binding.addButton.setOnClickListener {
            showAddWordDialog()
        }

        binding.deleteButton.setOnClickListener {
            showDeleteDialog()
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

    fun showEditDialog() {
        val dialogBinding = DialogPasswordBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialogBinding.run {
            dismissButton.setOnClickListener {
                dialog.dismiss()
            }
            submitButton.setOnClickListener {
                lifecycleScope.launch {
                    launchNetworkApi(this@DetailActivity) {
                        viewModel.authenticate(
                            password = passwordEdittext.text.toString(),
                            onSuccess = {
                                dialog.dismiss()
                            },
                            onFailure = {
                                Toast.makeText(this@DetailActivity, "잘못된 비밀번호입니다.", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
        dialog.show()
    }

    fun showDeleteDialog() {
        val dialogBinding = DialogDeleteWordlistBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialogBinding.run {
            dismissButton.setOnClickListener {
                dialog.dismiss()
            }
            submitButton.setOnClickListener {
                lifecycleScope.launch {
                    launchNetworkApi(this@DetailActivity) {
                        viewModel.deleteWordList()
                        dialog.dismiss()
                        setResult(RESULT_OK)
                        finish()
                    }
                }
            }
        }
        dialog.show()
    }

    fun showAddWordDialog() {
        val dialogBinding = DialogAddWordBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialogBinding.run {
            dismissButton.setOnClickListener {
                dialog.dismiss()
            }
            submitButton.setOnClickListener {
                lifecycleScope.launch {
                    launchNetworkApi(this@DetailActivity) {
                        viewModel.addWordToWordList(
                            spell = dialogBinding.spellEdittext.text.toString(),
                            meaning = dialogBinding.meaningEdittext.text.toString(),
                            synonym = dialogBinding.synonymEdittext.text.toString(),
                            antonym = dialogBinding.antonymEdittext.text.toString(),
                            sentence = dialogBinding.sentenceEdittext.text.toString(),
                            onSuccess = {
                                dialog.dismiss()
                            }
                        )
                    }
                }
            }
        }
        dialog.show()
    }
}