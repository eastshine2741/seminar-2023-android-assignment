package com.example.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment1.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("id")
        val userPw = intent.getStringExtra("pw")
        binding.userId = userId
        binding.userPw = userPw
        binding.operator = "+"
        binding.result = "0"

        binding.addButton.setOnClickListener {
            calculate("+")
        }
        binding.subtractButton.setOnClickListener {
            calculate("-")
        }
        binding.multiplyButton.setOnClickListener {
            calculate("*")
        }
        binding.divideButton.setOnClickListener {
            calculate("/")
        }
    }

    fun calculate(operator: String) {
        if (binding.firstEdittext.text.isNotEmpty() && binding.secondEdittext.text.isNotEmpty()) {
            val first = binding.firstEdittext.text.toString().toFloat()
            val second = binding.secondEdittext.text.toString().toFloat()
            binding.resultText.text = when (operator) {
                "+" -> (first+second).toString()
                "-" -> (first-second).toString()
                "*" -> (first*second).toString()
                else -> (first/second).toString()
            }
        }
        binding.operator = operator
    }
}