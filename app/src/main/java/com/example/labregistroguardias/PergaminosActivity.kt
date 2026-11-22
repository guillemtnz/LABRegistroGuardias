package com.example.labregistroguardias

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException

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

        // Escritura en el almacenamiento interno (files/)
        btnGuardarInterno.setOnClickListener {
            val nombre = etNombreArchivo.text.toString().trim()
            val texto = etContenido.text.toString()

            if (nombre.isNotEmpty()) {
                try {
                    // MODE_PRIVATE: solo esta app puede acceder al archivo
                    openFileOutput(nombre, Context.MODE_PRIVATE).use { flujoSalida ->
                        flujoSalida.write(texto.toByteArray())
                    }
                    Toast.makeText(this, "Pergamino guardado en la bóveda privada", Toast.LENGTH_SHORT).show()
                    etContenido.text.clear()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al escribir: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombreArchivo.error = "Especifica un nombre para el pergamino"
            }
        }

        // Lectura desde el almacenamiento interno (files/)
        btnLeerInterno.setOnClickListener {
            val nombre = etNombreArchivo.text.toString().trim()

            if (nombre.isNotEmpty()) {
                try {
                    val textoLeido = openFileInput(nombre).bufferedReader().use { lector ->
                        lector.readText()
                    }
                    etContenido.setText(textoLeido)
                    Toast.makeText(this, "Pergamino leído correctamente", Toast.LENGTH_SHORT).show()
                } catch (e: FileNotFoundException) {
                    etNombreArchivo.error = "Ese pergamino no existe en la bóveda privada"
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al leer: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombreArchivo.error = "Introduce el nombre del pergamino a leer"
            }
        }
    }
}
