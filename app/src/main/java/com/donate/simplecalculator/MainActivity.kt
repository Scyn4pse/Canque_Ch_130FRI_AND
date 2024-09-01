package com.donate.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber1: EditText
    private lateinit var editTextNumber2: EditText
    private lateinit var textViewResult: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUIComponents()
        setButtonListeners()

    }

    private fun initializeUIComponents() {
        editTextNumber1 = findViewById(R.id.editTextNumber1)
        editTextNumber2 = findViewById(R.id.editTextNumber2)
        textViewResult = findViewById(R.id.textViewResult)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubtract = findViewById(R.id.btnSubtract)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)
    }

    private fun setButtonListeners() {
        btnAdd.setOnClickListener {
            displayResult(calculate(editTextNumber1.text.toString(), editTextNumber2.text.toString(), '+'))
        }

        btnSubtract.setOnClickListener {
            displayResult(calculate(editTextNumber1.text.toString(), editTextNumber2.text.toString(), '-'))
        }

        btnMultiply.setOnClickListener {
            displayResult(calculate(editTextNumber1.text.toString(), editTextNumber2.text.toString(), '*'))
        }

        btnDivide.setOnClickListener {
            displayResult(calculate(editTextNumber1.text.toString(), editTextNumber2.text.toString(), '/'))
        }
    }

    private fun calculate(num1Str: String, num2Str: String, operation: Char): Double? {
        val num1 = num1Str.toDoubleOrNull()
        val num2 = num2Str.toDoubleOrNull()

        return if (num1 != null && num2 != null) {
            when (operation) {
                '+' -> num1 + num2
                '-' -> num1 - num2
                '*' -> num1 * num2
                '/' -> if (num2 != 0.0) num1 / num2 else null
                else -> null
            }
        } else {
            null
        }
    }

    private fun displayResult(result: Double?) {
        textViewResult.text = when {
            result == null -> "Invalid input"
            result % 1 == 0.0 -> result.toInt().toString()
            else -> String.format("%.2f", result)
        }
    }

}