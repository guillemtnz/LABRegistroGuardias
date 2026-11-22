package com.example.labregistroguardias

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PergaminosActivity : AppCompatActivity() {

    private lateinit var etNombreArchivo: EditText
    private lateinit var etContenido: EditText
    private lateinit var btnGuardarInterno: Button
    private lateinit var btnLeerInterno: Button
    private lateinit var btnGuardarExterno: Button
    private lateinit var btnLeerExterno: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergaminos)

        etNombreArchivo = findViewById(R.id.etNombreArchivo)
        etContenido = findViewById(R.id.etContenido)
        btnGuardarInterno = findViewById(R.id.btnGuardarInterno)
        btnLeerInterno = findViewById(R.id.btnLeerInterno)
        btnGuardarExterno = findViewById(R.id.btnGuardarExterno)
        btnLeerExterno = findViewById(R.id.btnLeerExterno)
    }
}
