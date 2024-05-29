package com.example.contentprovidertorrescallejasemiliano

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var capturar: ImageView
    private lateinit var urlImg: Uri
    private lateinit var tiempo: String
    private lateinit var nombreImagen: String

    private var contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        capturar.setImageURI(null)
        capturar.setImageURI(urlImg)
    }

    private fun createImagenUri():Uri {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tiempo = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").withZone(ZoneOffset.UTC).format(Instant.now())
        }

        nombreImagen = "$tiempo.png"

        val imagen = File(filesDir,nombreImagen)

        return FileProvider.getUriForFile(this, "com.example.contentprovidertorrescallejasemiliano.FileProvider", imagen)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        capturar = findViewById(R.id.captureImageView)
        val capturarImgBtn = findViewById<Button>(R.id.capturarImgBtn)

        capturarImgBtn.setOnClickListener{
            urlImg = createImagenUri()
            contract.launch(urlImg)
        }

    }
}