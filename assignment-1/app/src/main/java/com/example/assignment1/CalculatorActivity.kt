package com.example.assignment1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.assignment1.databinding.ActivityCalculatorBinding
import com.example.assignment1.viewmodel.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    private val _operator = MutableStateFlow<String>("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("id")
        val userPw = intent.getStringExtra("pw")
        binding.userId = userId
        binding.userPw = userPw

        binding.addButton.setOnClickListener {
            lifecycleScope.launch { _operator.emit("+") }
        }
        binding.subtractButton.setOnClickListener {
            lifecycleScope.launch { _operator.emit("-") }
        }
        binding.multiplyButton.setOnClickListener {
            lifecycleScope.launch { _operator.emit("*") }
        }
        binding.divideButton.setOnClickListener {
            lifecycleScope.launch { _operator.emit("/") }
        }
        lifecycleScope.launch {
            _operator.collect {
                if (binding.firstEdittext.text.isNotEmpty() && binding.secondEdittext.text.isNotEmpty()) {
                    val first = binding.firstEdittext.text.toString().toFloat()
                    val second = binding.secondEdittext.text.toString().toFloat()
                    binding.resultText.text = when (it) {
                        "+" -> (first+second).toString()
                        "-" -> (first-second).toString()
                        "*" -> (first*second).toString()
                        else -> (first/second).toString()
                    }
                }
                binding.operatorText.text = _operator.value
            }
        }
    }
}