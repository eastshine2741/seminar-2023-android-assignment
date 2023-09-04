package com.example.assignment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.assignment1.databinding.ActivityMainBinding
import com.example.assignment1.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            if (binding.pwEdittext.text.toString().length < 5) {
                Toast.makeText(this, "유효하지 않은 비밀번호입니다", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, CalculatorActivity::class.java)
            intent.putExtra("id", binding.idEdittext.text.toString())
            intent.putExtra("pw", binding.pwEdittext.text.toString())
            startActivity(intent)
        }
    }
}