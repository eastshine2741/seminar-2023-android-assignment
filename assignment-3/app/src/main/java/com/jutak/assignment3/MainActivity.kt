package com.jutak.assignment3

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jutak.assignment3.adapters.WordListAdapter
import com.jutak.assignment3.databinding.ActivityMainBinding
import com.jutak.assignment3.databinding.DialogCreateWordlistBinding
import com.jutak.assignment3.network.launchNetworkApi
import com.jutak.assignment3.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var activityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {       // wordlist deleted
                lifecycleScope.launch {
                    launchNetworkApi(this@MainActivity) {
                        viewModel.getWordLists()
                    }
                }
            }
        }

        binding.wordlistRecyclerView.adapter = WordListAdapter(onClickItem = { id ->
            activityLauncher.launch(
                Intent(this, DetailActivity::class.java)
                    .putExtra("wordListId", id)
            )
        })

        viewModel.wordLists.observe(this) {
            (binding.wordlistRecyclerView.adapter as WordListAdapter).run {
                submitList(it)
                notifyDataSetChanged()
            }
        }

        binding.addButton.setOnClickListener {
            showCreateWordListDialog()
        }

        lifecycleScope.launch {
            launchNetworkApi(this@MainActivity) {
                viewModel.getWordLists()
            }
        }
    }

    fun showCreateWordListDialog() {
        val dialogBinding = DialogCreateWordlistBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialogBinding.run {
            submitButton.setOnClickListener {
                lifecycleScope.launch {
                    launchNetworkApi(this@MainActivity) {
                        viewModel.createNewWordList(
                            name = nameEdittext.text.toString(),
                            owner = ownerEdittext.text.toString(),
                            password = passwordEdittext.text.toString()
                        )
                    }
                    dialog.dismiss()
                }
            }
            dismissButton.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}