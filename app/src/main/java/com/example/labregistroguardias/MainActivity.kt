package com.example.labregistroguardias

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etSoldado: EditText
    private lateinit var spinnerRango: Spinner
    private lateinit var switchEstado: Switch
    private lateinit var btnGuardar: Button
    private lateinit var tvUltimoRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etSoldado = findViewById(R.id.etSoldado)
        spinnerRango = findViewById(R.id.spinnerRango)
        switchEstado = findViewById(R.id.switchEstado)
        btnGuardar = findViewById(R.id.btnGuardar)
        tvUltimoRegistro = findViewById(R.id.tvUltimoRegistro)

        // Los rangos se cargan desde el string-array de strings.xml
        val rangos = resources.getStringArray(R.array.rangos_militares)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rangos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRango.adapter = adapter
    }
}