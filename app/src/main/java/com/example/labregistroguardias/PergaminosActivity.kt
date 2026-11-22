package com.example.labregistroguardias

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
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

        // Escritura en el almacenamiento externo privado (Documents/)
        btnGuardarExterno.setOnClickListener {
            val nombre = etNombreArchivo.text.toString().trim()
            val texto = etContenido.text.toString()

            if (!esAlmacenamientoExternoEscribible()) {
                Toast.makeText(this, "Error: el almacenamiento externo no está montado", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (nombre.isNotEmpty()) {
                try {
                    val rutaDirectorio = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                    val archivo = File(rutaDirectorio, nombre)
                    archivo.writeText(texto)
                    Toast.makeText(this, "Mapa guardado en Android/data/.../Documents/", Toast.LENGTH_SHORT).show()
                    etContenido.text.clear()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al escribir: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombreArchivo.error = "Introduce el nombre del mapa"
            }
        }

        // Lectura desde el almacenamiento externo privado (Documents/)
        btnLeerExterno.setOnClickListener {
            val nombre = etNombreArchivo.text.toString().trim()

            if (!esAlmacenamientoExternoLeible()) {
                Toast.makeText(this, "Error: soporte no disponible para lectura", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (nombre.isNotEmpty()) {
                try {
                    val rutaDirectorio = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                    val archivo = File(rutaDirectorio, nombre)

                    if (archivo.exists()) {
                        etContenido.setText(archivo.readText())
                        Toast.makeText(this, "Mapa cargado correctamente", Toast.LENGTH_SHORT).show()
                    } else {
                        etNombreArchivo.error = "Ese mapa no existe en los documentos externos"
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al leer: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombreArchivo.error = "Especifica qué mapa quieres consultar"
            }
        }
    }

    // Comprobaciones del estado del almacenamiento externo
    private fun esAlmacenamientoExternoEscribible(): Boolean {
        val estado = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == estado
    }

    private fun esAlmacenamientoExternoLeible(): Boolean {
        val estado = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == estado || Environment.MEDIA_MOUNTED_READ_ONLY == estado
    }
}
